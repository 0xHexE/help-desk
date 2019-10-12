const { Client, Variables, File, logger } = require('camunda-external-task-client-js');
const query = require('./queries');

const config = {
    baseUrl: 'http://localhost:8080/engine-rest',
    use: logger
};

const client = new Client(config);


    // todo
    // add validation for date nd time
client.subscribe('ValidateForm', async function ({ task, taskService }) {
    
    const name = task.variables.get('name');
    const date = task.variables.get('date');
    const time = task.variables.get('time213');

    const processVariables = new Variables();

    // check if they are not provided
    if (name === undefined || date === undefined || time === undefined) {
        processVariables.set('isValid', false);
    }else {
        let result = /^[a-zA-Z ]+$/.test(name);
        if (result == false) {
            processVariables.set('isValid', false);
        }else {
            processVariables.set('isValid', true);
        }
    }

    await taskService.complete(task, processVariables);
});

client.subscribe('CheckAvailability', async function ({ task, taskService }) {

    const name = task.variables.get('name');
    const date = task.variables.get('date');
    const time = task.variables.get('time');

    const processVariables = new Variables();
    processVariables.set('isAppointmentPossible', true);

    let resultMain = query.getAppointmentByDate(date);
    let resultTemp = query.getTempAppointmentByDate(date);

    console.log(resultMain);
    console.log(resultTemp);

    await taskService.complete(task, processVariables);
});

client.subscribe('AddToQueue', async function ({ task, taskService }) {

});