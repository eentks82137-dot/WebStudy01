const result1 = async () => {
  const res = await fetch(
    "http://localhost:8080/hw03/convert?from=km&to=mile&value=10",
    {
      headers: {
        Accept: "application/xml",
      },
    },
  );
  console.log(res.status, res.statusText);
  const text = await res.text();
  console.log(text);
};

result1();
