import passport from 'passport';
import { Strategy } from 'passport-local';
import user from '../models/user';

let init = () => {
	passport.use(new Strategy(
		function (username, password, done) {
			user.findOne({ username, password }).then((foundUser) => {
				return done(null, foundUser);
			}).catch((error) => {
				return done(null, false, error);
			});
		})
	);

	passport.serializeUser((sessionUser, cb) => {
		cb(null, sessionUser._id);
	});

	passport.deserializeUser((id, cb) => {
		user.findOne({ _id: id }).then((user) => {
			cb(null, user);
		});
	});
};

export default { init };


