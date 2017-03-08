import passport from 'passport';
import { Strategy } from 'passport-local';
import models from '../models/index';

let init = () => {
	passport.use(new Strategy(function (username, password, done) {
		console.log('auth');
		models.user.findOne({ username, password }).then((foundUser) => {
			console.log('found target user');
			console.log(foundUser);
			done(null, foundUser);
		}).catch((error) => {
			console.log('didn\'t found target user');
			done(null, false, error);
		});
	}));

	passport.serializeUser((user, cb) => {
		console.log('serialize');
		console.log(user);
		cb(null, user.getToken);
	});

	passport.deserializeUser((id, cb) => {
		console.log('deserialize');
		models.user.findOne({ _id: id }).then((user) => {
			cb(null, user);
		});
	});
};

export default { init };


