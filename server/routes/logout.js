import express from 'express';

let router = express.Router();

router.route('/logout')
	.get((req, res) => {
		req.logout();
		res.redirect('/welcome');
	});

export default router;