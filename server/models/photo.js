import mongoose from 'mongoose';
import { extname } from 'path';

let imageSchema = mongoose.Schema({
	imgData: Buffer,
	contentType: String,
	userId: String,
	date: Date,
	name: String,
	description: String,
	sizeKb: Number
});

let Image = mongoose.model('Image', imageSchema);

let addPhoto = (file, user, userData) => {
	return new Promise((resolve, reject) => {
		let contentType = 'image/';
		let fileExt = extname(file.originalname).toLowerCase().substring(1);
		switch (fileExt) {
			/* eslint-disable indent */
			case 'png':
			case 'jpeg': {
				contentType += fileExt;
				break;
			}
			case 'jpg': {
				contentType += 'jpeg';
				break;
			}
			default: {
				reject(new Error(`Image of unknown type ${fileExt} provided. Please use jpeg or png`));
			}
			/* eslint-enable indent */
		}
		let newImage = new Image({
			userId: user._id,
			date: new Date(),
			name: file.originalname,
			description: userData.description,
			sizeKb: file.size / 1024,
			imgData: file.buffer,
			contentType
		});
		newImage.save((err, result) => {
			if (err) {
				reject(err);
			}
			resolve(result);
		});
	});
};

let getUserPhotos = (user) => {
	return new Promise((resolve, reject) => {
		Image.find({ userId: user._id }, (err, result) => {
			if (err) {
				reject(err);
			}
			resolve(result);
		});
	});
};

let getPhotoById = (id) => {
	return new Promise((resolve, reject) => {
		Image.findOne({ _id: id }, (err, result) => {
			if (err) {
				reject(err);
			}
			resolve(result);
		});
	});
};

export default {
	addPhoto,
	getUserPhotos,
	getPhotoById
};

