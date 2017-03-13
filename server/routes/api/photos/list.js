import express from 'express';
import photo from '../../../models/photo';

let router = express.Router();

router.get('/list', (req, res) => {
	photo.getUserPhotos(req.user).then((result) => {
		res.json({
			result: result.map((photoObj) => {
				return {
					id: photoObj._id,
					sizeKb: photoObj.sizeKb,
					name: photoObj.name,
					description: photoObj.description,
					date: photoObj.date,
					userId: photoObj.userId
				}
			})
		});
	});
});

export default router;
