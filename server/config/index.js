import passport from 'passport';
import bodyParser from 'body-parser';
import expressSession from 'express-session';
import cookieParser from 'cookie-parser';
import morgan from 'morgan';

let configureApp = (app) => {
	app.set('views', './views');
	app.set('view engine', 'pug');
	app.use(morgan('common'));
	app.use(cookieParser());
	app.use(bodyParser.urlencoded({ extended: true }));
	app.use(expressSession({
		secret: 'secret',
		saveUninitialized: false,
		resave: false
	}));
	app.use(passport.initialize());
	app.use(passport.session());
};

export default configureApp;