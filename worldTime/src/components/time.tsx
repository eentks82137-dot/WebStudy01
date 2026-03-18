import { useEffect, useState } from "react";

interface WorldTime {
  now: string;
  locale: string;
  timezone: string;
}
interface TimeOptions {
  locales: Record<string, string>;
  timezones: Record<string, string>;
}

export default function Time() {
  const [time, setTime] = useState<WorldTime>();
  const [locale, setLocale] = useState<string>("ko-KR");
  const [timezone, setTimezone] = useState<string>("Asia/Seoul");

  const [options, setOptions] = useState<TimeOptions>();

  const getOptions = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/hw01/worldtime/options",
      );
      const data = await response.json();
      setOptions(data);
    } catch (error) {
      console.error("옵션을 불러오는 중 에러가 발생했습니다:", error);
    }
  };

  const getTime = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/hw01/worldtime/json?locale=${locale}&timezone=${timezone}`,
      );
      const data = await response.json();
      setTime(data);
    } catch (error) {
      console.error("데이터를 불러오는 중 에러가 발생했습니다:", error);
    }
  };

  useEffect(() => {
    getOptions();
  }, []);

  useEffect(() => {
    getTime();
  }, [locale, timezone]);

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>🌍 World Time</h2>

      <div style={styles.controlGroup}>
        <div style={styles.inputRow}>
          <label htmlFor="locale" style={styles.label}>
            Locale
          </label>
          <select
            name="locale"
            id="locale"
            value={locale}
            onChange={(e) => setLocale(e.target.value)}
            style={styles.select}
          >
            {options?.locales &&
              Object.entries(options.locales).map(([key, value]) => (
                <option key={key} value={key}>
                  {key} - {value}
                </option>
              ))}
          </select>
        </div>

        <div style={styles.inputRow}>
          <label htmlFor="timezone" style={styles.label}>
            Timezone
          </label>
          <select
            name="timezone"
            id="timezone"
            value={timezone}
            onChange={(e) => setTimezone(e.target.value)}
            style={styles.select}
          >
            {options?.timezones &&
              Object.entries(options.timezones).map(([key, value]) => (
                <option key={key} value={key}>
                  {key} - {value}
                </option>
              ))}
          </select>
        </div>
      </div>

      <div style={styles.resultBox}>
        {time ? (
          <>
            <p style={styles.mainTime}>{time.now}</p>
            <div style={styles.subInfo}>
              <p>📍 Locale: {time.locale}</p>
              <p>🕒 Timezone: {time.timezone}</p>
            </div>
          </>
        ) : (
          <p style={styles.loading}>시간 정보를 불러오는 중...</p>
        )}
      </div>
    </div>
  );
}

// 🎨 스타일 객체 모음
const styles = {
  container: {
    maxWidth: "420px",
    margin: "3rem auto",
    padding: "2rem",
    backgroundColor: "#ffffff",
    borderRadius: "16px",
    boxShadow: "0 10px 25px rgba(0, 0, 0, 0.08)",
    fontFamily: "system-ui, -apple-system, sans-serif",
  },
  title: {
    margin: "0 0 1.5rem 0",
    fontSize: "1.5rem",
    fontWeight: "700",
    color: "#1a1a1a",
    textAlign: "center" as const,
  },
  controlGroup: {
    display: "flex",
    flexDirection: "column" as const,
    gap: "1rem",
    marginBottom: "1.5rem",
  },
  inputRow: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  label: {
    fontWeight: "600",
    color: "#4a4a4a",
    fontSize: "0.95rem",
  },
  select: {
    padding: "0.6rem",
    width: "65%",
    borderRadius: "8px",
    border: "1px solid #d1d5db",
    backgroundColor: "#f9fafb",
    fontSize: "0.95rem",
    color: "#1f2937",
    cursor: "pointer",
    outline: "none",
  },
  resultBox: {
    padding: "1.5rem",
    backgroundColor: "#eff6ff", // 부드러운 파란색 배경
    borderRadius: "12px",
    border: "1px solid #bfdbfe",
    textAlign: "center" as const,
  },
  mainTime: {
    margin: "0 0 0.8rem 0",
    fontSize: "1.3rem",
    fontWeight: "800",
    color: "#1d4ed8", // 진한 파란색
  },
  subInfo: {
    fontSize: "0.9rem",
    color: "#4b5563",
    display: "flex",
    flexDirection: "column" as const,
    gap: "0.3rem",
    margin: 0,
  },
  loading: {
    color: "#6b7280",
    fontSize: "0.95rem",
    margin: 0,
  },
};
