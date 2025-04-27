document.addEventListener("DOMContentLoaded", function () {
  const productContainer = document.getElementById("productRows");
  const addRowBtn = document.getElementById("addRowBtn");
  const invoiceTotal = document.getElementById("invoiceTotal");

  let rowIndex = document.querySelectorAll(".product-row").length;

  function updateTotal() {
    let grandTotal = 0;
    document.querySelectorAll(".product-row").forEach((row) => {
      const quantity = parseFloat(row.querySelector(".quantity").value) || 0;
      const price = parseFloat(row.querySelector(".price").value) || 0;
      const tax = parseFloat(row.querySelector(".tax").value) || 0;

      const subtotal = quantity * price;
      const taxAmount = subtotal * (tax / 100);
      const total = subtotal + taxAmount;

      row.querySelector(".total").value = total.toFixed(2);
      grandTotal += total;
    });
    invoiceTotal.value = grandTotal.toFixed(2);
  }

  function bindRowEvents(row) {
    const productSelect = row.querySelector(".product-select");
    const quantityInput = row.querySelector(".quantity");

    productSelect.addEventListener("change", function () {
      const selected = productSelect.options[productSelect.selectedIndex];
      row.querySelector(".price").value = parseFloat(
        selected.getAttribute("data-price") || 0
      ).toFixed(2);
      row.querySelector(".tax").value = parseFloat(
        selected.getAttribute("data-tax") || 0
      ).toFixed(2);
      updateTotal();
    });

    quantityInput.addEventListener("input", updateTotal);

    row.querySelector(".remove-row").addEventListener("click", function () {
      row.remove();
      updateTotal();
    });
  }

  addRowBtn.addEventListener("click", function () {
    const newRow = document.querySelector(".product-row").cloneNode(true);
    newRow.querySelectorAll("input").forEach((input) => (input.value = ""));
    newRow.querySelector("select").selectedIndex = 0;

    newRow
      .querySelector("select")
      .setAttribute("name", `items[${rowIndex}].product.productId`);
    newRow
      .querySelector(".quantity")
      .setAttribute("name", `items[${rowIndex}].quantity`);
    newRow
      .querySelector(".price")
      .setAttribute("name", `items[${rowIndex}].price`);
    newRow.querySelector(".tax").setAttribute("name", `items[${rowIndex}].tax`);
    newRow
      .querySelector(".total")
      .setAttribute("name", `items[${rowIndex}].total`);

    rowIndex++;
    productContainer.appendChild(newRow);
    bindRowEvents(newRow);
  });

  document.querySelectorAll(".product-row").forEach(bindRowEvents);
});
