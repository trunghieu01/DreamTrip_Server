package iuh.edu.vn.dreamtrip.server.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.entity.TuongTac;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TuongTacServiceImp implements TuongTacService {
    ThongTinThongKeServiceImp thongTinThongKeServiceImp;
    Firestore dbFireStore = FirestoreClient.getFirestore();

    CollectionReference collectionReference = dbFireStore.collection("tuongTac");

    @Override
    public String insert(TuongTac tuongTac) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("tuongTac").document().set(tuongTac);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String update(TuongTac tuongTac) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbFireStore.collection("tuongTac").document(tuongTac.getDocument_id());
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot doc = future.get();
        if (doc.exists()) {
            ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("tour").document(tuongTac.getDocument_id()).set(tuongTac);
            return collectionApiFuture.get().getUpdateTime().toString();
        }
        return "Tuong Tac not exists";
    }

    @Override
    public List<String> getTourIdPlaned(String userId) throws ExecutionException, InterruptedException {
        return collectionReference.whereArrayContains("userLenKeHoach", userId).get().get().getDocuments().parallelStream().map(id -> {
            final var tourId = id.toObject(TuongTac.class).getTourId();
            return tourId;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getTourIdLiked(String userId) throws ExecutionException, InterruptedException {
        return collectionReference.whereArrayContains("userDaThich", userId).get().get().getDocuments().parallelStream().map(id -> {
            final var tourId = id.toObject(TuongTac.class).getTourId();
            return tourId;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getTourIdBooked(String userId) throws ExecutionException, InterruptedException {
        return collectionReference.whereArrayContains("userDaDat", userId).get().get().getDocuments().parallelStream().map(id -> {
            final var tourId = id.toObject(TuongTac.class).getTourId();
            return tourId;
        }).collect(Collectors.toList());
    }

    @Override
    public String like(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<String> listTT = dbFireStore.collection("tuongTac").whereEqualTo("tourId", tourId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tt = tour.toObject(TuongTac.class).getDocument_id();

            return tt;
        }).collect(Collectors.toList());
        return collectionReference.document(listTT.get(0)).update("userDaThich", FieldValue.arrayUnion(userId)).get().getUpdateTime().toString();
    }

    @Override
    public String unLike(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<String> listTT = dbFireStore.collection("tuongTac").whereEqualTo("tourId", tourId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tt = tour.toObject(TuongTac.class).getDocument_id();
            return tt;
        }).collect(Collectors.toList());
        return collectionReference.document(listTT.get(0)).update("userDaThich", FieldValue.arrayRemove(userId)).get().getUpdateTime().toString();
    }

    @Override
    public String book(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<String> listTT = dbFireStore.collection("tuongTac").whereEqualTo("tourId", tourId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tt = tour.toObject(TuongTac.class).getDocument_id();
            return tt;
        }).collect(Collectors.toList());
        return collectionReference.document(listTT.get(0)).update("userDaDat", FieldValue.arrayUnion(userId)).get().getUpdateTime().toString();
    }

    @Override
    public String plan(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<String> listTT = dbFireStore.collection("tuongTac").whereEqualTo("tourId", tourId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tt = tour.toObject(TuongTac.class).getDocument_id();
            return tt;
        }).collect(Collectors.toList());
        return collectionReference.document(listTT.get(0)).update("userLenKeHoach", FieldValue.arrayUnion(userId)).get().getUpdateTime().toString();
    }

    @Override
    public String dropOutPlan(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<String> listTT = dbFireStore.collection("tuongTac").whereEqualTo("tourId", tourId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tt = tour.toObject(TuongTac.class).getDocument_id();
            return tt;
        }).collect(Collectors.toList());
        return collectionReference.document(listTT.get(0)).update("userLenKeHoach", FieldValue.arrayRemove(userId)).get().getUpdateTime().toString();
    }

    @Override
    public boolean checkLike(String tourId, String userId) throws ExecutionException, InterruptedException {
        List list = collectionReference.whereEqualTo("tourId", tourId).whereArrayContains("userDaThich", userId).get().get().getDocuments().parallelStream().map(id -> {
            final var like = id.toObject(TuongTac.class).getTourId();
            return like;
        }).collect(Collectors.toList());
        System.out.println(list.size());
        if (list.size() != 0)
            return true;
        return false;
    }

    @Override
    public TuongTac findAllByTourId(String tourId) throws ExecutionException, InterruptedException {
        Query query = collectionReference.whereEqualTo("tourId", tourId);
        QuerySnapshot querySnapshot = query.get().get();
        List<TuongTac> tuongTacs = new ArrayList<>();
        for (QueryDocumentSnapshot tt : querySnapshot.getDocuments()) {
            TuongTac new_tt = tt.toObject(TuongTac.class);
            tuongTacs.add(new_tt);
        }
        return tuongTacs.get(0);
    }

}
