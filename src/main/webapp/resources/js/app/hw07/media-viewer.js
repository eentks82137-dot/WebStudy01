document.addEventListener("DOMContentLoaded", async () => {
  // 기존과 동일한 로직
  const response = await fetch("/hw07/media-list.json");
  const mediaMap = await response.json();
  const select = document.querySelector("#media");

  for (const [mediaType, mediaList] of Object.entries(mediaMap)) {
    const optgroup = select.querySelector(`optgroup[id=${mediaType}]`);

    mediaList.forEach((media) => {
      const option = document.createElement("option");
      option.value = media;
      option.textContent = media;
      optgroup.appendChild(option);
    });
  }

  select.addEventListener("change", (e) => {
    const selectedOption = e.target.value;
    const mediaType = e.target.selectedOptions[0].parentElement.id;
    let mediaPath = "";

    switch (mediaType) {
      case "videos":
      case "images":
      case "texts":
        mediaPath = `/hw07/media?file=${selectedOption}`;
        break;
    }

    const viewer = document.querySelector("#viewer");
    viewer.innerHTML = "";

    if (mediaType === "videos") {
      const video = document.createElement("video");
      video.src = mediaPath;
      video.controls = true;
      viewer.appendChild(video);
    } else if (mediaType === "images") {
      const img = document.createElement("img");
      img.src = mediaPath;
      viewer.appendChild(img);
    } else if (mediaType === "texts") {
      const iframe = document.createElement("iframe");
      iframe.src = mediaPath;
      viewer.appendChild(iframe);
    }
  });
});
