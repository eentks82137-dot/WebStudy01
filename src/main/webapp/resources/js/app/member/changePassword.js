document.addEventListener("DOMContentLoaded", async () => {
  const form = document.getElementById("changePasswordForm");
  const newPassword = document.getElementById("newPassword");
  const confirmPassword = document.getElementById("confirmPassword");

  const oldPasswordLabel = document.querySelector('label[for="oldPassword"]');
  const newPasswordLabel = document.querySelector('label[for="newPassword"]');
  const confirmPasswordLabel = document.querySelector(
    'label[for="confirmPassword"]',
  );

  newPassword.addEventListener("input", () => {
    if (newPassword.value.length < 8) {
      newPassword.setCustomValidity("비밀번호는 최소 8자 이상이어야 합니다.");
      newPassword.reportValidity();
      newPasswordLabel.classList.add("error-label");
    } else {
      newPassword.setCustomValidity("");
      newPasswordLabel.classList.remove("error-label");
    }
  });

  confirmPassword.addEventListener("input", () => {
    if (confirmPassword.value !== newPassword.value) {
      confirmPassword.setCustomValidity("비밀번호가 일치하지 않습니다.");
      confirmPassword.reportValidity();
      confirmPasswordLabel.classList.add("error-label");
    } else {
      confirmPassword.setCustomValidity("");
      confirmPasswordLabel.classList.remove("error-label");
    }
  });
});
