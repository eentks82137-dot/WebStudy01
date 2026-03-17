document.addEventListener("DOMContentLoaded", () => {
  const resultDiv = document.getElementById("result");
  const fetchBtn = document.getElementById("fetch-btn");
  fetchBtn.addEventListener("click", async () => {
    const locale = document.getElementById("locale").value;
    const timezone = document.getElementById("timezone").value;
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
