package com.college.converter.recipe;import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    private List<Recipe> lstFavorites;
    private RecyclerView myrv;
    // private DatabaseReference mRootRef;
    //  private FirebaseAuth mAuth;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        emptyView = RootView.findViewById(R.id.if_no_fav);
        myrv = RootView.findViewById(R.id.recyclerview_favorites);
        progressBar = RootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        //   getFavorites(RootView);
        return RootView;

    }

//

    ItemTouchHelper.SimpleCallback delete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder deleteRecipe = new AlertDialog.Builder(getContext());
            deleteRecipe.setTitle("Delete the Recipe?");
            deleteRecipe.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    String recipeId = lstFavorites.get(position).getId();
                    lstFavorites.remove(position);
                    Objects.requireNonNull(myrv.getAdapter()).notifyItemRemoved(position);
                    //        deleteFromFirebase(recipeId);
                }
            });
            deleteRecipe.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Objects.requireNonNull(myrv.getAdapter()).notifyItemChanged(viewHolder.getAbsoluteAdapterPosition());
                }
            });
            deleteRecipe.show();
        }

//
    };
}