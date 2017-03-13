import express from 'express';

let router = express.Router();

router.get('/home', (req, res) => {
	let user = req.user;
	res.render('home', { username: user.username });
});

export default router;