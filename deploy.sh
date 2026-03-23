#!/bin/bash

set -Eeuo pipefail

on_error() {
    local line_number="$1"
    echo "[deploy] ERROR: line ${line_number} failed" >&2
}

trap 'on_error ${LINENO}' ERR

log() {
    echo "[deploy] $*"
}

run_as_project_user() {
    if [[ -n "${SUDO_USER:-}" && "$SUDO_USER" != "root" ]]; then
        sudo -u "$SUDO_USER" -- "$@"
    else
        "$@"
    fi
}

wait_for_dev_context_release() {
    local attempts=0
    local max_attempts=10

    if ! command -v lsof >/dev/null 2>&1; then
        log "lsof is not available. Waiting briefly for Tomcat to undeploy the dev context."
        sleep 3
        return
    fi

    while lsof +D "$DEV_APP_DIR" >/dev/null 2>&1; do
        attempts=$((attempts + 1))
        if (( attempts >= max_attempts )); then
            log "Tomcat still has open files in $DEV_APP_DIR. Continuing with cleanup."
            return
        fi
        log "Waiting for Tomcat to release dev context files... ($attempts/$max_attempts)"
        sleep 1
    done
}

require_command() {
    local command_name="$1"

    if ! command -v "$command_name" >/dev/null 2>&1; then
        echo "[deploy] ERROR: required command not found: $command_name" >&2
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
STARTUP_SCRIPT="$TOMCAT_BASE/bin/startup.sh"
CATALINA_LOG="$TOMCAT_BASE/logs/catalina.out"
FOLLOW_LOGS="${FOLLOW_LOGS:-false}"

require_command xmllint
require_command mvn
require_command find
require_command pgrep
require_command sudo

if [[ $EUID -ne 0 ]]; then
    echo "[deploy] ERROR: run this script as root or with sudo" >&2
    exit 1
fi

log "Building WAR package"
run_as_project_user mvn -q -f "$PROJECT_ROOT/pom.xml" clean package -DskipTests

WAR_FILE=$(find "$PROJECT_ROOT/target" -maxdepth 1 -type f -name '*.war' | head -n 1)
if [[ -z "$WAR_FILE" ]]; then
    echo "[deploy] ERROR: no WAR file found in $PROJECT_ROOT/target" >&2
    exit 1
fi

log "Removing development deployment artifacts"
rm -f "$CONTEXT_FILE"

if pgrep -f 'org.apache.catalina.startup.Bootstrap' >/dev/null 2>&1; then
    wait_for_dev_context_release
fi

rm -rf "$DEV_APP_DIR"

log "Removing previous ROOT deployment"
rm -rf "$WEBAPPS_DIR/$CONTEXT_NAME" "$WEBAPPS_DIR/$CONTEXT_NAME.war"

log "Copying $(basename "$WAR_FILE") to $WEBAPPS_DIR/$CONTEXT_NAME.war"
cp "$WAR_FILE" "$WEBAPPS_DIR/$CONTEXT_NAME.war"

if pgrep -f 'org.apache.catalina.startup.Bootstrap' >/dev/null 2>&1; then
    log "Tomcat is already running. The new WAR should be deployed automatically."
else
    log "Tomcat is not running. Starting Tomcat."
    "$STARTUP_SCRIPT"
fi

log "Deployment completed"
log "WAR: $WAR_FILE"
echo "------------------------------------------"
if [[ "$FOLLOW_LOGS" == "true" ]]; then
    tail -f "$CATALINA_LOG"
else
    tail -n 40 "$CATALINA_LOG"
fi