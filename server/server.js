import express from 'express';
import passport from 'passport';
import bodyParser from 'body-parser';
import expressSession from 'express-session';
import cookieParser from 'cookie-parser';
import connect from 'connect-ensure-login';
import auth from './services/authenticationService.js';
import db from './services/dbConnectionService.js';
import models from './models/index';

db.init();
auth.init();

let app = express();

app.use(express.static(__dirname + '/public'));
app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(expressSession({ secret: 'pokemon', resave: true, saveUninitialized: true }));
app.use(passport.initialize());
app.use(passport.session());

app.get('/fail', (req, res) => {
	res.status(403);
	res.json({ error: 'illegal credentials provided' });
});

app.post('/login', passport.authenticate('local', {
	failureRedirect: '/fail'
}), (req, res) => {
	if (req.user) {
		res.json({ token: req.user._id });
	}
});

app.get('/login', (req, res) => {
	res.sendFile(__dirname + '/public/login.html');
});

// app.get('/', (req, res, next) => {
// 	console.log('\nhello\n');
// 	next();
// }, connect.ensureLoggedIn('/login'), (req, res) => {
// 	res.json({ main: 'success' });
// });



// app.get('/read', (req, res, next) => {
// 	models.user.getAllData().then((result) => {
// 		res.json(result);
// 	}).catch((err) => {
// 		res.json({ error: 1 });
// 	});
// });

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
	console.log('server successfully started');
});
