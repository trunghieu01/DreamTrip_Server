package iuh.edu.vn.dreamtrip.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.controller.HoatDongController;
import iuh.edu.vn.dreamtrip.server.entity.HoatDong;

@Service
public class HoatDongServiceImp implements HoatDongService {

	Logger logger = Logger.getLogger(HoatDongController.class.getName());
	Firestore dbFireStore = FirestoreClient.getFirestore();
	@Autowired
	TourService tourservice;

	@Override
	public String createHoatDong(HoatDong new_hoat_dong) throws InterruptedException, ExecutionException {
		ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("hoatDong").document().set(new_hoat_dong);
		return collectionApiFuture.get().getUpdateTime().toString();
	}

	@Override
	public List<HoatDong> getHoatDong(String tourID) throws InterruptedException, ExecutionException {
		Query query = dbFireStore.collection("hoatDong").orderBy("thoiGianHD").whereEqualTo("tourId", tourID);
		QuerySnapshot querySnapshot = query.get().get();
		List<HoatDong> listHd = new ArrayList<>();
		for (QueryDocumentSnapshot hd : querySnapshot.getDocuments()) {
			HoatDong Newhd = hd.toObject(HoatDong.class);
			listHd.add(Newhd);
		}
		return listHd;
	}

	@Override
	public String updateHoatDong(HoatDong hd) throws InterruptedException, ExecutionException {
		DocumentReference documentReference = dbFireStore.collection("hoatDong").document(hd.getId());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot doc = future.get();
		if (doc.exists()) {
			ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("hoatDong").document(hd.getId())
					.set(hd);
			return collectionApiFuture.get().getUpdateTime().toString();
		}
		return "Admin not exists";
	}

	@Override
	public String deleteHoatDong(String document_id) {
		ApiFuture<WriteResult> writeResult = dbFireStore.collection("hoatDong").document(document_id).delete();

		return "Successfully Deleted";
	}

	@Override
	public List<HoatDong> findAllsByTourId(String tourId) throws InterruptedException, ExecutionException {
		List<HoatDong> result = new ArrayList<HoatDong>();
		if (tourservice.getTour(tourId) != null) {
			List<HoatDong> hoatDongs =  dbFireStore.collection("hoatDong").orderBy("thoiGianHD").get().get().getDocuments().parallelStream().map(tour -> {
				final var tourDocument = tour.toObject(HoatDong.class);
				if (tourDocument.getTourId().equalsIgnoreCase(tourId)) {
					return tourDocument;
				}
				return null;
			}).collect(Collectors.toList());
			for (HoatDong hd: hoatDongs
				 ) {
				if(hd != null) {
					result.add(hd);
				}
			}
			return result;
		}
		logger.log(Level.SEVERE, "Không tìm thấy tour có id: " + tourId);
		return null;
	}

	@Override
	public List<HoatDong> findAlls() throws InterruptedException, ExecutionException {
		return dbFireStore.collection("hoatDong").get().get().getDocuments().parallelStream().map(tour -> {
			final var tourDocument = tour.toObject(HoatDong.class);
			return tourDocument;
		}).collect(Collectors.toList());
	}

	@Override
	public HoatDong findById(String id) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFireStore.collection("hoatDong").document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot doc = future.get();
		HoatDong hd;
		if (doc.exists()) {
			hd = doc.toObject(HoatDong.class);
			return hd;
		}
		return null;
	}

	@Override
	public String updateRating(String id, String comment, int rate) {
		dbFireStore.collection("hoatDong").document(id).update("binhLuan", comment, "danhGia", rate);
		return null;
	}
}
