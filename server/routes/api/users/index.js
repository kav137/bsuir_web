import express from 'express';
import createRoute from './create';
// import deleteRoute from '/delete';
// import editRoute from './edit';
import readRoute from './read';

let router = express.Router();

router.get('/', (req, res) => {
	res.send('~~ /api/users');
});
router.use(createRoute);
// router.use('./delete', deleteRoute);
// router.use('./edit', editRoute);
router.use(readRoute);

export default router;