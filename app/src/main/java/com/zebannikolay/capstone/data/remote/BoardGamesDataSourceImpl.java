package com.zebannikolay.capstone.data.remote;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.zebannikolay.capstone.domain.models.BoardGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public final class BoardGamesDataSourceImpl implements BoardGamesDataSource {

    private final DatabaseReference databaseReference;

    public BoardGamesDataSourceImpl(@NonNull final DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    @Override
    public Single<BoardGame> game(@NonNull final String id) {
        return Single.create(emitter ->
            databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final BoardGame game = dataSnapshot.getValue(BoardGame.class);
                    if (game == null) {
                        emitter.onError(new NullPointerException("game == null"));
                    }
                    emitter.onSuccess(game);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            })
        );
    }

    @Override
    public Single<HashMap<String, BoardGame>> games() {
        return Single.create(emitter ->
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<HashMap<String, BoardGame>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, BoardGame>>() {};
                    emitter.onSuccess(dataSnapshot.getValue(genericTypeIndicator));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            })
        );
    }

    @Override
    public Completable addGames(@NonNull final HashMap<String, BoardGame> games) {
        return Completable.create(emitter ->
            databaseReference.setValue(games)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError)

        );
    }
}
