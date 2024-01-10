import createPage from "../components/createPage";
import { createArea } from "./dashboardHelpers";

import * as MetricHelpers from "./helpers/metricsHelpers";
import * as ScheduleHelpers from "./helpers/scheduleHelpers";

async function dashboard() {
  createPage("Dashboard");
  const pageContent = document.querySelector("#pageContent");
  const scheduleArea = createArea("Schedules");
  const dayArea = createArea("Days");
  const eventArea = createArea("Events");
  const metricsArea = createArea("Metrics");

  pageContent.append(scheduleArea, dayArea, eventArea, metricsArea);

  await ScheduleHelpers.renderSchedules();
  await MetricHelpers.renderMetrics();
}

export default dashboard;
