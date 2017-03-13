import express from 'express';
import users from './users';
import photos from './photos';

let router = express.Router();

router.get('/', (req, res) => {
	res.send();
});
router.use('/users', users);
router.use('/photos', photos);

export default router;