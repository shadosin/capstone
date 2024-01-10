import ApiClient from "../api/apiClient";

const client = new ApiClient();

window.addEventSubmit = function (event) {};

window.renderEventItemOnForm = function (eventItem) {
  function generateListItems(eventDetails) {
    return Object.entries(eventDetails)
      .map(([key, value]) => `<li><strong>${key} : </strong>${value}</li>`)
      .join("");
  }

  const eventItemCard = document.querySelector("#eventItemCard");
  if (eventItemCard) {
    eventItemCard.remove();
  }

  function getName() {
    if (eventItem.name) {
      return eventItem.name;
    } else {
      return eventItem.exerciseName
    }
  }

  const formElement = document.querySelector("#addEventForm");
  const div = document.createElement("div");
  div.id = "eventItemCard";
  div.classList.add("eventItemCard", "card");
  div.innerHTML = `<h4><strong>${eventItem.name ?? eventItem.exerciseName}</strong></h4><ul>${generateListItems(eventItem)}</ul>`;

  const submitButton = document.createElement("input");
  submitButton.type = "submit";
  formElement.append(div, submitButton);
};

window.onEventSelect = async function (event) {
  const eventItemCard = document.querySelector("#eventItemCard");

  if (eventItemCard) {
    const eventItemCard = document.querySelector("#eventItemCard");
    if (eventItemCard) {
      eventItemCard.remove();
    }
  }

  let eventItem;
  console.log(event.target.id, event.target.value);

  if (event.target.id === "mealType") {
    const mealSelected = document.querySelector("#mealSelected").value;
    console.log({ mealSelected });
    if (mealSelected) {
      eventItem = await client.getMealEvent(mealSelected);
    }
  }

  if (event.target.id === "exerciseType") {
    const exerciseSelected = document.querySelector("#exerciseSelected").value;
    if (exerciseSelected) {
      eventItem = await client.getExerciseEvent(exerciseSelected);
    }
  }

  console.log({ eventItem });

  renderEventItemOnForm(eventItem);
};

window.onTypeSelect = async function (event) {
  const formElement = document.querySelector("#addEventForm");
  const eventItemCard = document.querySelector("#eventItemCard");

  if (eventItemCard) {
    eventItemCard.remove();
  }

  const mealSelect = document.querySelector("#mealSelected");
  if (mealSelect) {
    mealSelect.remove();
  }

  const exerciseSelect = document.querySelector("#exerciseSelected");
  if (exerciseSelect) {
    exerciseSelect.remove();
  }

  console.log(event.target.id, event.target.value);

  const selectElement = document.createElement("select");

  if (event.target.id === "mealType") {
    try {
      const response = await client.getAllMealEvents(event.target.value);
      selectElement.id = "mealSelected";
      selectElement.name = "mealSelected";
      selectElement.required = true;
      selectElement.innerHTML = `<option value="" disabled selected>Select a Meal</option>`;
      selectElement.innerHTML = response
        .map((meal) => {
          return `<option id=${meal.mealId} value=${meal.mealId}>${meal.name}</option>`;
        })
        .join("");
    } catch (error) {
      console.error("Error in getAllMealEvents:", error);
    }
  }

  if (event.target.id === "exerciseType") {
    try {
      const response = await client.getAllExerciseEvents(event.target.value);
      selectElement.id = "exerciseSelected";
      selectElement.name = "exerciseSelected";
      selectElement.required = true;
      selectElement.innerHTML = response;
      selectElement.innerHTML = `<option value="" disabled selected>Select an Exercise</option>`;
      selectElement.innerHTML += response
        .map((exercise) => {
          console.log({ exercise }  )
          return `<option id=${exercise.exerciseId} value=${exercise.exerciseId}>${exercise.exerciseName}</option>`;
        })
        .join("");
    } catch (error) {
      console.error("Error in getAllExerciseEvents:", error);
    }
  }

  formElement.append(selectElement);
  selectElement.addEventListener("click", () => {
    selectElement.addEventListener("change", onEventSelect(event));
  });
};

window.eventOnClick = function (event) {
  const eventItemCard = document.querySelector("#eventItemCard");

  if (eventItemCard) {
    eventItemCard.remove();
  }

  const data = document.getElementById("data");
  const element = document.querySelector("#addEventForm");

  const select = document.querySelector(".eventType");
  if (select) {
    select.remove();
  }
  const mealSelect = document.querySelector("#mealSelected");
  if (mealSelect) {
    mealSelect.remove();
  }
  const exerciseSelect = document.querySelector("#exerciseSelected");
  if (exerciseSelect) {
    exerciseSelect.remove();
  }

  const mealTypes = ["Breakfast", "Lunch", "Dinner", "Snack"];
  const exerciseTypes = ["Cardio", "Strength", "Flexibility"];
  const typeSelect = document.createElement("select");
  typeSelect.classList.add("eventType");
  typeSelect.required = true;

  if (event.target.value === "Meal") {
    typeSelect.id = "mealType";
    typeSelect.name = "mealType";
    typeSelect.innerHTML = `<option value="" disabled selected>Select a Meal Type</option>`;
    typeSelect.innerHTML += mealTypes
      .map((mealType) => {
        return `<option value="${mealType}">${mealType}</option>`;
      })
      .join("");
  } else if (event.target.value === "Exercise") {
    typeSelect.id = "exerciseType";
    typeSelect.name = "exerciseType";
    typeSelect.innerHTML =
      `<option value="" disabled selected>Select a Exercise Type</option>` +
      exerciseTypes
        .map((exerciseType) => {
          return `<option value="${exerciseType}">${exerciseType}</option>`;
        })
        .join("");
  }

  element.append(typeSelect);
  typeSelect.addEventListener("click", () => {
    typeSelect.addEventListener("change", onTypeSelect);
  });
};

export function addEventForm() {
  return `
    <div class="add-event-form">
      <form id="addEventForm" onsubmit="addEventSubmit(event)">
      <select id="type" name="type" required onclick="eventOnClick(event)">
        <option value="Meal">Meal</option>
         <option value="Exercise">Exercise</option>
         </select>
         
         <label id="data" for="data"></label>
         
        </form>
    </div>
  `;
}
