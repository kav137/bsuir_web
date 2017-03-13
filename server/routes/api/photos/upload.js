import express from 'express';
import multer from 'multer';
import photo from '../../../models/photo';

let storage = multer.memoryStorage();
var upload = multer({ storage: storage });
let router = express.Router();

router.route('/upload')
	.get((req, res) => {
		res.render('photos/upload');
	})
	.post(upload.single('photo'), (req, res) => {
		photo.addPhoto(req.file, req.user, {
			description: req.body.description
		}).then((uploadedPhoto) => {
			res.json({ success: true, photoId: uploadedPhoto._id });
		}).catch((error) => {
			res.json({ error });
		});
	});

export default router;
