package iuh.edu.vn.dreamtrip.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.entity.Tour;

@Service
public class TourServiceImp implements TourService {

    Firestore dbFireStore = FirestoreClient.getFirestore();

    CollectionReference collectionReference = dbFireStore.collection("tour");
    Query query = collectionReference.orderBy("tenTour");

    @Override
    public Tour insertTour(Tour tour) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFireStore.collection("tour").document();
        ApiFuture<WriteResult> collectionApiFuture = documentReference.set(tour);
       if (collectionApiFuture.get().getUpdateTime() != null){
           ApiFuture<DocumentSnapshot> future = documentReference.get();
           DocumentSnapshot doc = future.get();
           if (doc.exists()) {
               tour = doc.toObject(Tour.class);
               return tour;
           }else {
               return null;
           }
       }
        return null;
    }

    @Override
    public Tour getTour(String document_id) throws InterruptedException, ExecutionException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("tour").document(document_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot doc = future.get();
        Tour tour;
        if (doc.exists()) {
            tour = doc.toObject(Tour.class);
            tour.getTenTour();
            return tour;
        }
        return null;
    }

    @Override
    public String updateTour(Tour tour) throws InterruptedException, ExecutionException {
        DocumentReference documentReference = dbFireStore.collection("tour").document(tour.getDocument_id());
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot doc = future.get();
        if (doc.exists()) {
            ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("tour").document(tour.getDocument_id())
                    .set(tour);
            return collectionApiFuture.get().getUpdateTime().toString();
        }
        return "Tour not exists";
    }

    @Override
    public String updateRatingTour(List<Integer> ratingList, String tourId) throws InterruptedException, ExecutionException {
        float rating = 0;
        if (ratingList.size() != 0) {
            for (int i = 0; i < ratingList.size(); i++) {
                rating += ratingList.get(i);
            }
            rating = rating / ratingList.size();
        }
        return collectionReference.document(tourId).update("danhGia", rating).get().getUpdateTime().toString();
    }


    @Override
    public String deleteTour(String document_id) {

        ApiFuture<WriteResult> writeResult = dbFireStore.collection("tour").document(document_id).delete();
        return "Successfully Deleted tour : " + document_id;
    }

    @Override
    public List<Tour> findAlls() throws InterruptedException, ExecutionException {

        return dbFireStore.collection("tour").get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(Tour.class);
            return tourDocument;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Tour> findByCategory(String cate) throws ExecutionException, InterruptedException {
        Query query = collectionReference.orderBy("theLoai");
        QuerySnapshot querySnapshot = query.get().get();
        List<Tour> tours = new ArrayList<>();
        for (QueryDocumentSnapshot tour : querySnapshot.getDocuments()) {
            Tour new_tour = tour.toObject(Tour.class);
            for (String theLoai : new_tour.getTheLoai()
            ) {
                if (theLoai.equalsIgnoreCase(cate.toLowerCase())) {
                    tours.add(new_tour);
                }
            }
        }
        return tours;
    }


    @Override
    public List<Tour> findTrending() throws ExecutionException, InterruptedException {
        Query query = collectionReference.whereEqualTo("xuHuong", true);
        QuerySnapshot querySnapshot = query.get().get();
        List<Tour> tours = new ArrayList<>();
        for (QueryDocumentSnapshot tour : querySnapshot.getDocuments()) {
            Tour new_tour = tour.toObject(Tour.class);
            tours.add(new_tour);
        }
        return tours;
    }


    @Override
    public List<Tour> findPopular() throws ExecutionException, InterruptedException {
        Query query = collectionReference.whereEqualTo("phoBien", true);
        QuerySnapshot querySnapshot = query.get().get();
        List<Tour> tours = new ArrayList<>();
        for (QueryDocumentSnapshot tour : querySnapshot.getDocuments()) {
            Tour new_tour = tour.toObject(Tour.class);
            tours.add(new_tour);
        }
        return tours;
    }

    @Override
    public List<Tour> findByNameAndCate(String name, String cate) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = collectionReference.whereArrayContains("theLoai", cate).get().get();
        List<Tour> tours = new ArrayList<>();
        for (QueryDocumentSnapshot tour : querySnapshot.getDocuments()) {
            Tour new_tour = tour.toObject(Tour.class);
            if (new_tour.getTenTour().toLowerCase().contains(name.toLowerCase())) {
                tours.add(new_tour);
            }
        }
        return tours;
    }

    @Override
    public List<Tour> searchTourByName(String tourName) throws InterruptedException, ExecutionException {
        QuerySnapshot querySnapshot = query.get().get();
        List<Tour> tours = new ArrayList<>();
        for (QueryDocumentSnapshot tour : querySnapshot.getDocuments()) {
            Tour new_tour = tour.toObject(Tour.class);
            if (new_tour.getTenTour().toLowerCase().contains(tourName.toLowerCase())) {
                tours.add(new_tour);
            }
        }
        return tours;
    }
}
