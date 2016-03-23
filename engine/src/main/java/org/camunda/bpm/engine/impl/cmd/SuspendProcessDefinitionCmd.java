/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmd;

import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.impl.jobexecutor.TimerSuspendProcessDefinitionHandler;
import org.camunda.bpm.engine.impl.management.UpdateJobDefinitionSuspensionStateBuilderImpl;
import org.camunda.bpm.engine.impl.persistence.entity.SuspensionState;
import org.camunda.bpm.engine.impl.repository.UpdateProcessDefinitionSuspensionStateBuilderImpl;
import org.camunda.bpm.engine.impl.runtime.UpdateProcessInstanceSuspensionStateBuilderImpl;

/**
 * @author Daniel Meyer
 * @author Joram Barrez
 * @author roman.smirnov
 */
public class SuspendProcessDefinitionCmd extends AbstractSetProcessDefinitionStateCmd {

  public SuspendProcessDefinitionCmd(UpdateProcessDefinitionSuspensionStateBuilderImpl builder) {
    super(builder);
  }

  @Override
  protected SuspensionState getNewSuspensionState() {
    return SuspensionState.SUSPENDED;
  }

  @Override
  protected String getDelayedExecutionJobHandlerType() {
    return TimerSuspendProcessDefinitionHandler.TYPE;
  }

  @Override
  protected AbstractSetJobDefinitionStateCmd getSetJobDefinitionStateCmd(UpdateJobDefinitionSuspensionStateBuilderImpl jobDefinitionSuspensionStateBuilder) {
    return new SuspendJobDefinitionCmd(jobDefinitionSuspensionStateBuilder);
  }

  @Override
  protected SuspendProcessInstanceCmd getNextCommand(UpdateProcessInstanceSuspensionStateBuilderImpl processInstanceCommandBuilder) {
    return new SuspendProcessInstanceCmd(processInstanceCommandBuilder);
  }

  @Override
  protected String getLogEntryOperation() {
    return UserOperationLogEntry.OPERATION_TYPE_SUSPEND_PROCESS_DEFINITION;
  }

}
