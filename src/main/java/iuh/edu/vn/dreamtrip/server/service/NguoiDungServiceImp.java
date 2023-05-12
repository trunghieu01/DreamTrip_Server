package iuh.edu.vn.dreamtrip.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.*;
import iuh.edu.vn.dreamtrip.server.entity.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.dto.TaiKhoanAdminUserDTO;
import iuh.edu.vn.dreamtrip.server.entity.NguoiDung;

@Service
public class NguoiDungServiceImp implements NguoiDungService {
	Firestore dbFireStore = FirestoreClient.getFirestore();
	@Autowired
	private TaiKhoanServiceImp tkDB;
	
	@Override
	public String insertNguoiDung(TaiKhoanAdminUserDTO tk_user_dto) throws InterruptedException, ExecutionException {
		ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("nguoiDung").document().set(tk_user_dto.getNguoiDung());
		return collectionApiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String insert(NguoiDung tk_user_dto) throws InterruptedException, ExecutionException {
		ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("nguoiDung").document().set(tk_user_dto);
		return collectionApiFuture.get().getUpdateTime().toString();
	}

	@Override
	public NguoiDung getNguoiDung(String document_id) throws InterruptedException, ExecutionException {
		DocumentReference documentReference = dbFireStore.collection("nguoiDung").document(document_id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot doc = future.get();
		NguoiDung admin;
		if (doc.exists()) {
			admin = doc.toObject(NguoiDung.class);
			return admin;
		}
		return null;
	}

	@Override
	public NguoiDung getNguoiDungByEmail(String email) throws InterruptedException, ExecutionException {
		CollectionReference collectionReference = dbFireStore.collection("nguoiDung");
		Query query = collectionReference.whereEqualTo("email", email);
		QuerySnapshot querySnapshot = query.get().get();
		List<NguoiDung> nguoiDungs = new ArrayList<>();
		for (QueryDocumentSnapshot nd : querySnapshot.getDocuments()) {
			NguoiDung nguoiDung = nd.toObject(NguoiDung.class);
			nguoiDungs.add(nguoiDung);
		}
		if (nguoiDungs.size() >0) {
			return nguoiDungs.get(0);
		}
		System.out.println("Don't exit user");
		return null;
	}

	@Override
	public String updateNguoiDung(NguoiDung nguoiDung) throws InterruptedException, ExecutionException {
		
		DocumentReference documentReference = dbFireStore.collection("nguoiDung").document(nguoiDung.getDocument_id());
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot doc = future.get();
		if (doc.exists()) {
			ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("nguoiDung").document(nguoiDung.getDocument_id()).set(nguoiDung);
			return collectionApiFuture.get().getUpdateTime().toString();
		}
		return null;
	}
	@Override
	public String deleteNguoiDung(String document_id) {
		ApiFuture<WriteResult> writeResult = dbFireStore.collection("nguoiDung").document(document_id).delete();
		 //Oncomplete listener to check when the above^ write has been completed
	
		return "Successfully Deleted";
		
	}

}
