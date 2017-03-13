import passport from 'passport';
import { Strategy } from 'passport-local';
import models from '../models/index';

let init = () => {
	passport.use(new Strategy(
		function (username, password, done) {
			models.user.findOne({ username, password }).then((foundUser) => {
				return done(null, foundUser);
			}).catch((error) => {
				return done(null, false, error);
			});
		})
	);

	passport.serializeUser((user, cb) => {
		cb(null, user._id);
	});

	passport.deserializeUser((id, cb) => {
		models.user.findOne({ _id: id }).then((user) => {
			cb(null, user);
		});
	});
};

export default { init };


