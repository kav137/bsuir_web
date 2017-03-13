import express from 'express';
import user from '../../../models/user';

let router = express.Router();

router.route('/create')
	.get((req, res) => {
		res.render('createUser');
	})
	.post((req, res) => {
		let username = req.body.username;
		let password = req.body.password;
		let postRender = req.body.render;
		if (username && password) {
			user.createUser(username, password)
				.catch((err) => {
					req.error = err;
					res.redirect('/error');
				})
				.then((result) => {
					if (postRender) {
						res.render('userCreated', { result });
					}
					res.json({
						message: 'user successfully created',
						result
					});
				});
		}
	});

export default router;