const { Pool } = require('pg');

const pool = new Pool({
    user: 'postgres',
    host: 'localhost',
    database: 'dr-animo',
    password: 'void',
    port: 5432
});

const query = async (q) => {
    const client = await pool.connect();

    let result;
    try {
        result = await client.query({
            rowMode: 'array',
            text: q,
        });
    } catch (error) {
        throw error;
    } finally {
        client.end();
    }

    return result;
}

// format for return is YYYY-MM-DD
const getIso8601Date = (date) => {
    let d = new Date(date);
    if (d.toString().toLowerCase() == 'invalid date') {
        return 'Invalid Date';
    }

    let day = d.getDate();
    let month = d.getMonth()+1;
    let year = d.getFullYear();

    return `${year}-${month}-${day}`;
}


const createMainSchema = () => {
    pool.query('CREATE TABLE appointments ( id SERIAL PRIMARY KEY, name VARCHAR, date DATE, time DATE );', (error, results) => {
        if(error) 
            return error;

        return results.rows;
    });
}

const createTempSchema = () => {
    pool.query('CREATE TABLE temp_appointments ( id SERIAL PRIMARY KEY, name VARCHAR, date DATE, time DATE );', (error, results) => {
        if(error) 
            return error;

        return results.rows;
    });
}

const getAppointmentByDate = async (date) => {

    // handle error here for invalid date
    let dateObj = new Date(date);
    let dateIso8601 = getIso8601Date(dateObj.toISOString());

    let q = `select * from appointments where date = '${dateIso8601}';`;

    let result;
    try {
        result = query(q);
    } catch (error) {
        console.log(error);
    }

    return result;
}

const getTempAppointmentByDate = async (date) => {
    
    // handle error here for invalid date
    let dateObj = new Date(date);
    let dateIso8601 = getIso8601Date(dateObj.toISOString());

    let dateIso8601 = getIso8601Date(dateObj.toISOString());

    let query = `select * from temp_appointments where date = ${dateIso8601}`;

    pool.query(query, (error, results) => {
        if(error) 
            return error;

        return results.rows;
    });
}

exports = {
    getAppointmentByDate,
    getTempAppointmentByDate
}