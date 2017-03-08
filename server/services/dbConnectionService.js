import mongoose from 'mongoose';

let init = () => {
	console.log('Connecting to database...');
	mongoose.connect('mongodb://localhost/test');
	let connection = mongoose.connection;
	connection.on('error', (err) => {
		console.error(`Unable to connect to mongodb :: ${err}`);
	});
	connection.once('open', () => {
		console.log('Successfully connected to mongodb');
	});
};

export default { init };

