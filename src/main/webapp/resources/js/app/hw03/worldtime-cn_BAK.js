document.addEventListener("DOMContentLoaded", async () => {
  const myLocale = navigator.language || navigator.userLanguage;
  const myTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  const options = await fetch("http://localhost:8080/hw03/worldtime/options", {
    headers: {
      Accept: "application/json",
    },
  });
  const data = await options.json();

  const locales = data.localeMap;
  const timezones = data.timeZoneMap;

  const localeSelect = document.getElementById("locale-csr");
  const tzSelect = document.getElementById("timezone-csr");

  const frag = document.createDocumentFragment();
  const tzFrag = document.createDocumentFragment();

  for (const locale in locales) {
    frag.appendChild(
      new Option(
        `${locale} - ${locales[locale]}`,
        locale,
        locale === myLocale ? true : false,
        locale === myLocale ? true : false,
      ),
    );
  }
  for (const tz in timezones) {
    tzFrag.appendChild(
      new Option(
        `${tz} - ${timezones[tz]}`,
        tz,
        tz === myTimezone ? true : false,
        tz === myTimezone ? true : false,
      ),
    );
  }
  localeSelect.replaceChildren(frag);
  tzSelect.replaceChildren(tzFrag);

  // // Object.entries()는 객체의 키-값 쌍을 배열로 반환하는 함수
  // Object.entries(timezones).map(([k, v]) => {
  //   const option = new Option(v, k);
  //   tzFrag.appendChild(option);
  // });

  const resultDiv = document.getElementById("result");
  const asyncForm = document.getElementById("async-form");
  asyncForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const locale = document.getElementById("locale-csr").value;
    const timezone = document.getElementById("timezone-csr").value;
    const response = await fetch(
      `http://localhost:8080/hw01/worldtime/json?locale=${locale}&timezone=${timezone}`,
    );
    const data = await response.json();
    resultDiv.innerHTML = `<strong>Locale:</strong> ${data.locale} <br>
                    <strong>Timezone:</strong> ${data.timezone} <br>
                    <strong>Current Time:</strong> ${data.now}`;
    console.log(data);
  });
});
