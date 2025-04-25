// Validate customer form before submission
function validateProductsForm() {
  const keyword = document.getElementById("keyword").value.trim();

  if (keyword !== " ") {
    alert("Please enter a keyword.");
    return false;
  } else if (!/^[a-zA-Z0-9]$/.test(keyword)) {
    alert("Please enter a Valid keyword.");
    return false;
  }

  return true;
}
