import express from 'express';
import connect from 'connect-ensure-login';
import authService from './services/authenticationService.js';
import dbService from './services/dbConnectionService.js';
import configureApp from './config';
import { secureRouter, publicRouter } from './routes';

dbService.init();
authService.init();

let app = express();
configureApp(app);

app.get('/error', (req, res) => {
	if (req.statusCode) {
		res.status(req.statusCode);
	}
	let error = req.error || 'unknown error';
	res.json({ error });
});

app.use(publicRouter);
app.use(connect.ensureLoggedIn(), secureRouter);

// app.get('/delete', (req, res, next) => {
// 	models.user.deleteAll().then((result) => {
// 		res.json({ success: 1 });
// 	}).catch((err) => {
// 		res.json({ error: 1 });
// 	});
// });

// app.get('/initUsers', (req, res, next) => {
// 	models.user.insertInitialData().then((result) => {
// 		res.json(result);
// 	}).catch((err) => {
// 		res.json({ error: 1 });
// 	});
// });

app.listen(3001, () => {
	console.log('Server is running on the port 3001');
});
