import express from 'express';
import user from '../../../models/user';

let router = express.Router();

router.get('/list', (req, res) => {
	user.getAllData()
		.catch((err) => {
			req.error = err;
			res.redirect('/error');
		})
		.then((result) => {
			res.json(result);
		});
});

export default router;