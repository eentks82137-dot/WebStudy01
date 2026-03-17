document.addEventListener("DOMContentLoaded", async () => {
  const options = await fetch("http://localhost:8080/hw01/worldtime/options");
  const data = await options.json();
  const locales = data.locales;
  const timezones = data.timezones;

  let localeOptions = "";
});
