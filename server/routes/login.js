import express from 'express';
import passport from 'passport';

let router = express.Router();

router.route('/login')
	.get((req, res) => {
		res.render('login');
	})
	.post(passport.authenticate('local'), (req, res) => {
		res.json({ token: req.user._id });
	});

export default router;