import express from 'express';

let router = express.Router();

router.get('/welcome', (req, res) => {
	res.render('welcome');
});

export default router;