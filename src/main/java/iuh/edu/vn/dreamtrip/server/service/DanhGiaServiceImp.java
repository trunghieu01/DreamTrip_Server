package iuh.edu.vn.dreamtrip.server.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.entity.DanhGia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DanhGiaServiceImp implements DanhGiaService {

    Firestore dbFireStore = FirestoreClient.getFirestore();

    @Override
    public DanhGia insert(DanhGia danhGia) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = dbFireStore.collection("danhGia").whereEqualTo("nguoiDungID", danhGia.getNguoiDungID()).whereEqualTo("hoatDongID", danhGia.getHoatDongID()).get().get();
        boolean check = true;
        for (QueryDocumentSnapshot hd : querySnapshot.getDocuments()) {
            if (hd.toObject(DanhGia.class).getThoiGian().compareTo(new Date()) < 7)
                check = false;
        }
        if (check) {
            dbFireStore.collection("danhGia").document().create(danhGia).get().getUpdateTime();
            return danhGia;
        }
        return null;
    }

    @Override
    public List<DanhGia> getDanhGia(String userId, boolean status) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = dbFireStore.collection("danhGia").whereEqualTo("nguoiDungID", userId).whereEqualTo("status", status).get().get();
        List<DanhGia> list = new ArrayList<>();
        for (QueryDocumentSnapshot hd : querySnapshot.getDocuments()) {
            DanhGia dg = hd.toObject(DanhGia.class);
            list.add(dg);
        }
        return list;
    }

    @Override
    public String delete(String danhGiaId) throws ExecutionException, InterruptedException {
        dbFireStore.collection("danhGia").document(danhGiaId).delete().get().getUpdateTime();
        return "Đã xóa đánh giá: " + danhGiaId;
    }

    @Override
    public String update(int rate, String comment, String id) throws ExecutionException, InterruptedException {
        if (!dbFireStore.collection("danhGia").document(id).get().get().toObject(DanhGia.class).isStatus())
            return dbFireStore.collection("danhGia").document(id).update("danhGia", rate, "binhLuan", comment, "status", true).get().getUpdateTime().toString();
        return "Failed";
    }

    @Override
    public List<String> getByUserId(String userId, boolean status) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = dbFireStore.collection("danhGia").whereEqualTo("nguoiDungID", userId).whereEqualTo("status", status).get().get();
        List<String> list = new ArrayList<>();
        for (QueryDocumentSnapshot hd : querySnapshot.getDocuments()) {
            String dg = hd.toObject(DanhGia.class).getHoatDongID();
            list.add(dg);
        }
        return list;
    }

    @Override
    public List<Integer> getForRatingTour(String tourId) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = dbFireStore.collection("danhGia").whereEqualTo("tourId", tourId).whereEqualTo("status", true).get().get();
        List<Integer> list = new ArrayList<>();
        for (QueryDocumentSnapshot hd : querySnapshot.getDocuments()) {
            int rate = hd.toObject(DanhGia.class).getDanhGia();
            list.add(rate);
        }
        return list;
    }
}
