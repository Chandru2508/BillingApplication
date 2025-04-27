// Validate customer form before submission
function validateCustomerForm() {
  const name = document.getElementById("customerName").value.trim();
  const mobile = document.getElementById("customerMobile").value.trim();
  const email = document.getElementById("customerEmail").value.trim();

  if (!/^[0-9]{10}$/.test(mobile)) {
    alert("Please enter a valid 10-digit mobile number.");
    return false;
  }

  if (email !== "" && !email.includes("@gmail.com")) {
    alert("Please enter a valid email address.");
    return false;
  }

  return true;
}
