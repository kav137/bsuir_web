import express from 'express';
import photo from '../../../models/photo';

let router = express.Router();

router.get('/download/:photoId', (req, res) => {
	let id = req.params.photoId;
	if (!id) {
		res({ result: null });
	}
	photo.getPhotoById(id).catch((error) => {
		res.json(error);
	}).then((result) => {
		res.setHeader('Content-Type', result.contentType);
		res.end(new Buffer(result.imgData, 'binary'));
	});
});

export default router;
