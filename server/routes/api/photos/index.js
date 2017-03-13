import express from 'express';
import downloadRouter from './download';
import listRouter from './list';
import uploadRouter from './upload';

let router = express.Router();

router.use(downloadRouter);
router.use(uploadRouter);
router.use(listRouter);

export default router;
