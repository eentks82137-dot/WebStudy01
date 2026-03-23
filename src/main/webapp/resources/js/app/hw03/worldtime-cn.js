function fnMapToOption(map, frag, myLocale) {
  for (const value in map) {
    frag.appendChild(
      new Option(
        `${value} - ${map[value]}`,
        value,
        value === myLocale ? true : false,
        value === myLocale ? true : false,
      ),
    );
  }
  return frag;
}

document.addEventListener("DOMContentLoaded", async () => {
  const myLocale = navigator.language || navigator.userLanguage;
  const myTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  const options = await fetch("./options", {
    headers: {
      Accept: "application/json",
    },
  });
  const data = await options.json();

  const locales = data.localeMap;
  const timezones = data.timeZoneMap;

  const localeSelect = document.getElementById("locale-csr");
  const tzSelect = document.getElementById("timezone-csr");

  let frag = document.createDocumentFragment();
  let tzFrag = document.createDocumentFragment();

  frag = fnMapToOption(locales, frag, myLocale);
  tzFrag = fnMapToOption(timezones, tzFrag, myTimezone);

  localeSelect.replaceChildren(frag);
  tzSelect.replaceChildren(tzFrag);

  const asyncForm = document.getElementById("async-form");
  asyncForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const fd = new FormData(asyncForm);
    const params = new URLSearchParams(fd);
    const resultDiv = document.getElementById("result");
    const response = await fetch(`${asyncForm.action}?${params.toString()}`, {
      headers: {
        Accept: "application/json",
      },
    });
    const data = await response.json();
    resultDiv.innerHTML = `<strong>Locale:</strong> ${data.locale} <br>
                      <strong>Timezone:</strong> ${data.timezone} <br>
                      <strong>Current Time:</strong> ${data.now}`;
  });
});
