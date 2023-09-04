export function renderRInput() {
  let selectElement =
    document.querySelector<HTMLSelectElement>("select#r-input")!;
  for (let r = 1; r <= 5; r++) {
    selectElement.innerHTML += `
            <option value="${r}">${r}</option>
        `;
  }
}
