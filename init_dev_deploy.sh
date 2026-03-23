#!/bin/bash

set -Eeuo pipefail

on_error() {
    local line_number="$1"
    echo "[init-dev-deploy] ERROR: line ${line_number} failed" >&2
}

trap 'on_error ${LINENO}' ERR

log() {
    echo "[init-dev-deploy] $*"
}

run_as_project_user() {
    if [[ -n "${SUDO_USER:-}" && "$SUDO_USER" != "root" ]]; then
        sudo -u "$SUDO_USER" -- "$@"
    else
        "$@"
    fi
}

require_command() {
    local command_name="$1"

    if ! command -v "$command_name" >/dev/null 2>&1; then
        echo "[init-dev-deploy] ERROR: required command not found: $command_name" >&2
        exit 1
    fi
}

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
PROJECT_ROOT="$SCRIPT_DIR"
TOMCAT_BASE="${TOMCAT_BASE:-/opt/tomcat}"
CONTEXT_NAME="${CONTEXT_NAME:-ROOT}"
ARTIFACT_ID=$(xmllint --xpath 'string(/*[local-name()="project"]/*[local-name()="artifactId"])' "$PROJECT_ROOT/pom.xml")
DEV_APP_BASE="${DEV_APP_BASE:-$TOMCAT_BASE/devapps}"
DEV_APP_DIR="${DEV_APP_DIR:-$DEV_APP_BASE/${ARTIFACT_ID,,}-${CONTEXT_NAME,,}}"
WEBAPPS_DIR="$TOMCAT_BASE/webapps"
CONTEXT_DIR="$TOMCAT_BASE/conf/Catalina/localhost"
CONTEXT_FILE="$CONTEXT_DIR/${CONTEXT_NAME}.xml"
SOURCE_WEBAPP_DIR="$PROJECT_ROOT/src/main/webapp"
SOURCE_WEBINF_DIR="$SOURCE_WEBAPP_DIR/WEB-INF"
TARGET_CLASSES_DIR="$PROJECT_ROOT/target/classes"
DEV_LIB_DIR="$PROJECT_ROOT/target/dev-lib"
STARTUP_SCRIPT="$TOMCAT_BASE/bin/startup.sh"

require_command xmllint
require_command mvn
require_command find
require_command pgrep
require_command sudo

if [[ $EUID -ne 0 ]]; then
    echo "[init-dev-deploy] ERROR: run this script as root or with sudo" >&2
    exit 1
fi

if [[ ! -d "$SOURCE_WEBAPP_DIR" ]]; then
    echo "[init-dev-deploy] ERROR: source webapp directory not found: $SOURCE_WEBAPP_DIR" >&2
    exit 1
fi

if [[ ! -d "$SOURCE_WEBINF_DIR" ]]; then
    echo "[init-dev-deploy] ERROR: WEB-INF directory not found: $SOURCE_WEBINF_DIR" >&2
    exit 1
fi

log "Compiling classes and preparing runtime dependencies"
run_as_project_user mvn -q -f "$PROJECT_ROOT/pom.xml" -DskipTests compile dependency:copy-dependencies -DincludeScope=runtime -DoutputDirectory="$DEV_LIB_DIR"

log "Removing previous ROOT deployment entries"
rm -rf "$WEBAPPS_DIR/$CONTEXT_NAME" "$WEBAPPS_DIR/$CONTEXT_NAME.war"

log "Recreating development app directory"
mkdir -p "$DEV_APP_BASE" "$DEV_APP_DIR" "$DEV_APP_DIR/WEB-INF" "$CONTEXT_DIR"
find "$DEV_APP_DIR" -mindepth 1 -maxdepth 1 -exec rm -rf -- {} +
mkdir -p "$DEV_APP_DIR/WEB-INF"

log "Linking web resources from src/main/webapp"
find "$SOURCE_WEBAPP_DIR" -mindepth 1 -maxdepth 1 ! -name WEB-INF -exec ln -sfn -- {} "$DEV_APP_DIR/" \;

log "Linking WEB-INF resources except classes and lib"
find "$SOURCE_WEBINF_DIR" -mindepth 1 -maxdepth 1 ! -name classes ! -name lib -exec ln -sfn -- {} "$DEV_APP_DIR/WEB-INF/" \;

log "Linking compiled classes and runtime libraries"
ln -sfn -- "$TARGET_CLASSES_DIR" "$DEV_APP_DIR/WEB-INF/classes"
ln -sfn -- "$DEV_LIB_DIR" "$DEV_APP_DIR/WEB-INF/lib"

log "Writing Tomcat context file"
cat > "$CONTEXT_FILE" <<EOF
<Context docBase="$DEV_APP_DIR" reloadable="true">
    <Resources allowLinking="true" />
</Context>
EOF

if pgrep -f 'org.apache.catalina.startup.Bootstrap' >/dev/null 2>&1; then
    log "Tomcat is already running. The updated context file should be detected automatically."
    log "If the new ROOT app does not appear immediately, restart Tomcat once."
else
    log "Tomcat is not running. Starting Tomcat."
    "$STARTUP_SCRIPT"
fi

log "Development deployment is ready."
log "DocBase: $DEV_APP_DIR"
log "Classes: $TARGET_CLASSES_DIR"
log "Libraries: $DEV_LIB_DIR"