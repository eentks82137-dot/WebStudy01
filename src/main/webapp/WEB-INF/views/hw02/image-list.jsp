<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Image List</title>
</head>
<body>
    <h1>Image List</h1>

    <div>
        <h1>SSR</h1>
        <div>
            <select name="imageName" id="imageName1">
                <c:forEach var="image" items="${imageList}">
                    <option value="${image}" ${cookie.lastImage.value eq image ? "selected" : ""}>${image}</option>
                </c:forEach>
            </select>
            <button onclick="submitForm(1)">Submit</button>
        </div>
    </div>


    <div>
        <h1>CSR</h1>
            <div>
                <select name="imageName" id="imageName2" onchange="getImage(this.value)">
                    <option value="">Select an image</option>
                </select>
                            <button onclick="submitForm(2)">Submit</button>

            </div>

    </div>
<div>
    <img id="imagePreview" src="" alt="">

</div>
    <script>
    
        const submitForm = (type) => {
            const selectElement = type === 1 ? document.getElementById("imageName1") : document.getElementById("imageName2");
            const selectedValue = selectElement.value;
            location.href = `http://localhost:8080/hw02/image?imageName=\${selectedValue}`;
        }

        document.addEventListener("DOMContentLoaded", async () => {
            const response = await fetch("http://localhost:8080/hw02/imageList/json");
            const imageList = await response.json();
            console.log(imageList);
            const frag = document.createDocumentFragment();
            imageList.forEach(image => {
                const option = new Option(image, image);
                frag.appendChild(option);
            });
            document.getElementById("imageName2").appendChild(frag);

            const lastImageCookie = document.cookie.split("; ").find(cookie => cookie.startsWith("lastImage="));
            if (lastImageCookie) {
                const lastImage = lastImageCookie.split("=")[1];
                getImage(lastImage);
                document.getElementById("imageName2").value = lastImage;
            }
        })

        const getImage = async (imageName) => {
            if (imageName === "") return;
            const response = await fetch(`http://localhost:8080/hw02/image?imageName=\${imageName}`);
            const blob = await response.blob();
            const imageUrl = URL.createObjectURL(blob);
            const imgPreview = document.getElementById("imagePreview");
            imgPreview.src = imageUrl;
            return imageUrl;
        }
    </script>
</body>
</html>