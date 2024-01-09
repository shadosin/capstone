import ApiClient from "../api/apiClient";


const client = new ApiClient();

window.addEventSubmit = function (event) {

}

window.eventOnSelect = function (event) {
  const type = event.target.value;
  const data = document.getElementById("data");
if (type === "Meal") {

}
else if (type === "Exercise") {
  data.innerText = "Adding an Exercise";
}

  // if (type === "Meal") {
  //   data.innerHTML = `
  //   <label for="data">Meal Name</label>
  //   <input type="text" id="data" name="data" required>
  //   `;
  // } else if (type === "Exercise") {
  //   data.innerHTML = `
  //   <label for="data">Exercise Name</label>
  //   <input type="text" id="data" name="data" required>
  //   `;
  // }

}

export function addEventForm() {
  return `
    <div class="add-event-form">
      <form onsubmit="addEventSubmit(event)">
      <select id="type" name="type" required onchange="eventOnSelect(event)">
        <option value="Meal">Meal</option>
         <option value="Exercise">Exercise</option>
         </select>
         
         <label id="data" for="data"></label>
         
        </form>
    </div>
  `;
}