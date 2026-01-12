package com.example.jazzlibrary2025v2.presentation.gallery;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jazzlibrary2025v2.R;
import com.example.jazzlibrary2025v2.domain.model.Artist;
import com.example.jazzlibrary2025v2.domain.model.BootStrap;
import com.example.jazzlibrary2025v2.domain.model.DataCache;
import com.example.jazzlibrary2025v2.domain.model.ResultSetForUI;
import com.example.jazzlibrary2025v2.domain.model.Duration;
import com.example.jazzlibrary2025v2.domain.model.Instrument;
import com.example.jazzlibrary2025v2.domain.model.Type;
import com.example.jazzlibrary2025v2.domain.model.Video;
import com.example.jazzlibrary2025v2.presentation.gallery.adapters.VideoAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.jazzlibrary2025v2.utils.GlobalConfig.fancy_pathbar_sliding;
import static com.example.jazzlibrary2025v2.presentation.gallery.adapters.PaginationScrollListener.PAGE_START;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GalleryFragment extends Fragment {


    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout swipeRefreshLayout;

    ImageView mainPathBarLogo;

    private ChipGroup instrumentChipGroup;
    private ChipGroup artistChipGroup;
    private ChipGroup typeChipGroup;
    private ChipGroup durationChipGroup;
    public ChipGroup getDurationChipGroup() {
        return durationChipGroup;
    }
    public void setDurationChipGroup(ChipGroup durationChipGroup) {
        this.durationChipGroup = durationChipGroup;
    }
    public ChipGroup getTypeChipGroup() {
        return typeChipGroup;
    }
    public void setTypeChipGroup(ChipGroup typeChipGroup) {
        this.typeChipGroup = typeChipGroup;
    }
    public ChipGroup getArtistChipGroup() {
        return artistChipGroup;
    }
    public void setArtistChipGroup(ChipGroup artistChipGroup) {
        this.artistChipGroup = artistChipGroup;
    }
    public ChipGroup getInstrumentChipGroup() {
        return instrumentChipGroup;
    }
    public void setInstrumentChipGroup(ChipGroup instrumentChipGroup) {
        this.instrumentChipGroup = instrumentChipGroup;
    }

    private TextView byInstrumentTextView;
    private TextView byArtistTextView;
    private TextView byTypeTextView;
    private TextView byDurationTextView;
    public TextView getByDurationTextView() {
        return byDurationTextView;
    }
    public void setByDurationTextView(TextView byDurationTextView) {
        this.byDurationTextView = byDurationTextView;
    }
    public TextView getByTypeTextView() {
        return byTypeTextView;
    }
    public void setByTypeTextView(TextView byTypeTextView) {
        this.byTypeTextView = byTypeTextView;
    }
    public TextView getByArtistTextView() {
        return byArtistTextView;
    }
    public void setByArtistTextView(TextView byArtistTextView) {
        this.byArtistTextView = byArtistTextView;
    }
    public TextView getByInstrumentTextView() {
        return byInstrumentTextView;
    }
    public void setByInstrumentTextView(TextView byInstrumentTextView) {
        this.byInstrumentTextView = byInstrumentTextView;
    }


    BootStrap cachedData;
    boolean cashedDataAreValidFLAG = true;

    static LinearLayout mainPathBarLinearLayout;
    static LinearLayout pathBarLinearLayout;

    View mainLogoPathbarLayout ;

    View drawerProgressBarBookGIF;
    ProgressBar videosLoadingProgressBar;

    private static final int INSTRUMENT_TAG = 1;
    private static final int ARTIST_TAG = 2;
    private static final int TYPE_TAG = 3;
    private static final int DURATION_TAG = 4;

    private static final String INSTRUMENT_TAG_STRING = "1";
    private static final String ARTIST_TAG_STRING = "2";
    private static final String TYPE_TAG_STRING = "3";
    private static final String DURATION_TAG_STRING = "4";

    boolean keepArtistChipSelected = true;
    static ResultSetForUI resultSet  = null;





    //VIDEO LOADIN
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private final int ITEMS_PER_PAGE = 5;
    private int totalVideos = 0; // Initialize with your total video count
    private static final int VISIBLE_THRESHOLD = 3; // Load next page when 3 items from bottom
    public LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);



    private VideoAdapter videoAdapter;
    private RecyclerView videoRecyclerView;
    private List<Video> videoLista = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        drawerLayout = view.findViewById(R.id.drawer_layout);

        FindViewByIdComponents(view);
        // Setup RecyclerView with pagination
        setupRecyclerView();

        initRightNavView(drawerLayout);


        retrieveCashedData();

        resultSet = getResultSetByFilterPath(filterPathReader());
        if (resultSet != null && resultSet.getVideoList() != null) {
            totalVideos = resultSet.getVideoList().size();
        }

        instrumentChipGroupPopulator(resultSet.getInstrumentList());
        artistChipGroupPopulator(resultSet.getArtistList());
        durationChipGroupPopulator(resultSet.getDurationList());
        typeChipGroupPopulator(resultSet.getTypeList());


        loadVideos(currentPage);
//loadDataSetToUI()
        //TODO// SEARCH BAR


        // H O T


        swipeRefreshLayout.setVisibility(View.VISIBLE);


// LISTENERS
        mainPathBarLogo.setOnClickListener(v -> toggleDrawer());

        if (fancy_pathbar_sliding) {
            drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            private boolean isOpening = false;
            private boolean isClosing = false;
            private float lastSlideOffset = 0f;

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // Detect direction
                if (slideOffset > lastSlideOffset + 0.01f) {
                    // Opening phase
                    if (!isOpening) {
                        isOpening = true;
                        isClosing = false;
                        mainPathBarLinearLayout.setVisibility(View.VISIBLE);
                    }
                } else if (slideOffset < lastSlideOffset - 0.01f) {
                    // Closing phase
                    if (!isClosing) {
                        isClosing = true;
                        isOpening = false;
                    }
                }
                lastSlideOffset = slideOffset;

                // Handle alpha based on state
                if (isOpening) {
                    // Fade in: 0 → 1
                    float alpha = slideOffset;
                    mainPathBarLogo.setAlpha(alpha);
                    mainPathBarLinearLayout.setAlpha(alpha);
                } else if (isClosing) {
                    float alpha = 1;
                    mainPathBarLogo.setAlpha(alpha);
                    mainPathBarLinearLayout.setAlpha(alpha);


                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Ensure full visibility when opened

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Ensure full visibility when opened

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Optional: Handle state changes
            }
        });
        }


        //TODO// DEN douleuei to refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Perform refresh operation
            refreshVideoLayout();
        });


        return view;
    }




    //OVERIDE LIFECYCLE
    @Override
    public void onPause() {
        super.onPause();
        pauseAllPlayers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseAllPlayers();
    }

    private void pauseAllPlayers() {
        for (int i = 0; i < linearLayoutManager.getChildCount(); i++) {
            View view = linearLayoutManager.getChildAt(i);
            YouTubePlayerView playerView = view.findViewById(R.id.youtube_player_view);
            if (playerView != null) {
                playerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                    youTubePlayer.pause();
                });
            }
        }
    }

    private void releaseAllPlayers() {
        for (int i = 0; i < linearLayoutManager.getChildCount(); i++) {
            View view = linearLayoutManager.getChildAt(i);
            YouTubePlayerView playerView = view.findViewById(R.id.youtube_player_view);
            if (playerView != null) {
                playerView.release();
            }
        }
    }


    private void loadNextPage() {
        currentPage++;
        loadVideos(currentPage);
    }

    private void loadVideos(int page) {
        isLoading = true;
        videoAdapter.addLoadingFooter();

        // Simulate API call delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Get videos for current page (replace with your actual data source)
            List<Video> newVideos = getVideosForPage(page);

            // Remove loading footer
            videoAdapter.removeLoadingFooter();
            isLoading = false;

            isLastPage = newVideos.size() < ITEMS_PER_PAGE;

            if (newVideos.isEmpty()) {
                isLastPage = true;
            } else {
                videoAdapter.addVideos(newVideos);

                // Check if we've loaded all available videos
                if (videoAdapter.getItemCount() >= totalVideos) {
                    isLastPage = true;
                }
            }
        }, 1000); // Simulated network delay
    }


    private void retrieveCashedData() {
        if (DataCache.getInstance() != null) {
            cachedData = DataCache.getInstance().getBootstrapData();
            if (cachedData != null){
                //Toast.makeText(getContext(), "got the data", Toast.LENGTH_SHORT).show();
                cashedDataAreValidFLAG = true;

            } //TODO // check all bootstrap fields are valid, before use
        }
        else{
            //Toast.makeText(getContext(), "cashed data is corrupted, reload!", Toast.LENGTH_SHORT).show();
            cashedDataAreValidFLAG = false;
        }
    }

    private void FindViewByIdComponents(View rootView) {
        mainPathBarLogo = rootView.findViewById(R.id.main_jazzLiLogo);
        mainPathBarLinearLayout = rootView.findViewById(R.id.mainPathBarLinearLayout);
        pathBarLinearLayout = rootView.findViewById(R.id.pathBarLinearLayout);


        drawerProgressBarBookGIF = rootView.findViewById(R.id.filterViewProgressBar);
        videosLoadingProgressBar = rootView.findViewById(R.id.paginationProgressBar);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefresh);
        videoRecyclerView = rootView.findViewById(R.id.video_pagination_recycler_view);


    }



    private void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);  // Changed to END to match right-side drawer
        }
    }

    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        }
        return false;
    }

    public void initRightNavView(DrawerLayout drawerLayout) {


        setInstrumentChipGroup(instrumentChipGroup = drawerLayout.findViewById(R.id.instrumentChipGroup));
        setArtistChipGroup(artistChipGroup = drawerLayout.findViewById(R.id.artistChipGroup));
        setTypeChipGroup(typeChipGroup = drawerLayout.findViewById(R.id.typeChipGroup));
        setDurationChipGroup(durationChipGroup = drawerLayout.findViewById(R.id.durationChipGroup));

        setByInstrumentTextView(byInstrumentTextView = drawerLayout.findViewById(R.id.byInstrumentTextView));
        setByArtistTextView(byArtistTextView = drawerLayout.findViewById(R.id.byArtistTextView));
        setByTypeTextView(byTypeTextView = drawerLayout.findViewById(R.id.byTypeTextView));
        setByDurationTextView(byDurationTextView = drawerLayout.findViewById(R.id.byDurationTextView));

    }
//TODO// CASH BIG DATASETS!!  ARTISTS or create a fragment to put them in ,, idk drawer got super heavy!!
    void instrumentChipGroupPopulator(List<Instrument> instrumentList) {
        instrumentChipGroup.removeAllViews();
        for (Instrument instrument :  instrumentList) {

            Chip chip = chipForDrawerGenerator(instrument,INSTRUMENT_TAG);
            instrumentChipGroup.addView(chip);
        }


    }
    void artistChipGroupPopulator(List<Artist> artistList) {
        artistChipGroup.removeAllViews();
        for (Artist artist :  artistList) {

            Chip chip = chipForDrawerGenerator(artist, ARTIST_TAG);
            artistChipGroup.addView(chip);
        }

    }
    void typeChipGroupPopulator(List<Type> typeList) {
        typeChipGroup.removeAllViews();
        for (Type type :  typeList) {

            Chip chip = chipForDrawerGenerator(type,TYPE_TAG);
            typeChipGroup.addView(chip);
        }


    }
    void durationChipGroupPopulator(List<Duration> durationList) {
        durationChipGroup.removeAllViews();
        for (Duration duaration :  durationList) {

            Chip chip = chipForDrawerGenerator(duaration,DURATION_TAG);
            durationChipGroup.addView(chip);
        }


    }

    public <T> Chip chipForDrawerGenerator(T data, int CHIP_TAG){
        Chip chipForDrawer = null;

        switch (CHIP_TAG) {
        case INSTRUMENT_TAG:
            Instrument instrument = (Instrument) data;
            chipForDrawer = (Chip) LayoutInflater.from(instrumentChipGroup.getContext())
                    .inflate(R.layout.chip_drawer_instrument, instrumentChipGroup, false);

            chipForDrawer.setTag(INSTRUMENT_TAG);
            chipForDrawer.setText(instrument.getInstrumentName());
            chipForDrawer.setId(instrument.getInstrumentId());

            if(instrument.getInstrumentVideoCount()<1) {
                chipForDrawer.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipNoVideos)));
                chipForDrawer.setCheckable(false);
                chipForDrawer.setAlpha(0.5f);
            }


        break;
        case ARTIST_TAG:
            Artist artist = (Artist) data;
            chipForDrawer = (Chip) LayoutInflater.from(artistChipGroup.getContext())
                    .inflate(R.layout.chip_drawer_artist, artistChipGroup, false);

            chipForDrawer.setTag(ARTIST_TAG);
            chipForDrawer.setText(artist.getArtistFullName());
            chipForDrawer.setId(artist.getArtistId());
            if(artist.getArtistVideoCount()<1) {
                chipForDrawer.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipNoVideos)));
                chipForDrawer.setCheckable(false);
                chipForDrawer.setAlpha(0.5f);


            }

        break;
        case TYPE_TAG:
            Type type = (Type) data;
            chipForDrawer = (Chip) LayoutInflater.from(typeChipGroup.getContext())
                    .inflate(R.layout.chip_drawer_type, typeChipGroup, false);

            chipForDrawer.setTag(TYPE_TAG);
            chipForDrawer.setText(type.getTypeName());
            chipForDrawer.setId(type.getTypeId());
            if(type.getTypeVideoCount()<1) {
                chipForDrawer.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipNoVideos)));
                chipForDrawer.setCheckable(false);
                chipForDrawer.setAlpha(0.5f);

            }


            break;
        case DURATION_TAG:
            Duration duration = (Duration) data;
            chipForDrawer = (Chip) LayoutInflater.from(durationChipGroup.getContext())
                    .inflate(R.layout.chip_drawer_duration, durationChipGroup, false);

            chipForDrawer.setTag(DURATION_TAG);
            chipForDrawer.setText(duration.getDurationName());
            chipForDrawer.setId(duration.getDurationId());
            if(duration.getDurationVideoCount()<1) {
                chipForDrawer.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipNoVideos)));
                chipForDrawer.setCheckable(false);
                chipForDrawer.setAlpha(0.5f);
            }

        break;
        default:
            throw new IllegalArgumentException("error:: category Tag :: " + CHIP_TAG);
        }


        Chip finalChipForDrawer = chipForDrawer;

        // Add click listener
        chipForDrawer.setOnClickListener(v -> {
            // Handle instrument selection
            boolean selected = ((Chip) v).isChecked();
            if (selected) {
                onChipFromDrawerSelected(finalChipForDrawer, String.valueOf(CHIP_TAG));
            } else {
                onChipFromDrawerDeselected(String.valueOf(CHIP_TAG));
            }
        });





        return chipForDrawer;
    }


    // Example selection handlers
    @SuppressLint("SetTextI18n")
    private void onChipFromDrawerSelected(Chip chipDrawer, String CHIP_TAG) {

//        drawerProgressBarBookGIF.setVisibility(View.VISIBLE);
//        videosLoadingProgressBar.setVisibility(View.VISIBLE);

        Chip newPathChip = null;


        switch (Integer.parseInt(CHIP_TAG)) {
            case INSTRUMENT_TAG:

                newPathChip = configureNewPathChip(chipDrawer, INSTRUMENT_TAG);

                //at any case desselect artist chip
                removerPathChip(ARTIST_TAG_STRING, filterPathReader());
                artistChipGroup.clearCheck();

                break;
            case ARTIST_TAG:
                //when called instrument chip listener is trigered(that desselects artist chip), but i rechech the artist chip manualy
                //TODO// ADD PAGINATION LOADING , SCROLLING LOADING CHIRCLE ICON,
                //TODO// ADD SEARCH BAR     ΟΡ    ALPHABETSPINER


                newPathChip = configureNewPathChip(chipDrawer, ARTIST_TAG);

                // -1 γιατι το list μετράει απο την θέση 0!
                int chipDrawerIdToListId = chipDrawer.getId()-1;
                int thisArtistsInstrumentId = cachedData.getArtistList().get(chipDrawerIdToListId).getInstrumentId();
                Chip targetChip = instrumentChipGroup.findViewById(thisArtistsInstrumentId);
                if (targetChip != null && !targetChip.isChecked()) { //this IF will always be true

                    instrumentChipGroup.check(targetChip.getId());

                    Chip newInstrumentPathChip = configureNewPathChip(targetChip, INSTRUMENT_TAG);

                    //Toast.makeText(getContext(), String.valueOf(newInstrumentPathChip.getId()), Toast.LENGTH_SHORT).show();

                    addPathchip(INSTRUMENT_TAG_STRING, newInstrumentPathChip);

                }

                // manualy check the sellected chip
                //artistChipGroup.check(newPathChip.getId());


                break;
            case TYPE_TAG:

                newPathChip = configureNewPathChip(chipDrawer, TYPE_TAG);

                break;
            case DURATION_TAG:

                newPathChip = configureNewPathChip(chipDrawer, DURATION_TAG);

                break;
            default:

                newPathChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                newPathChip.setText("onChipFromDrawerSelected error");
                break;
        }


        addPathchip(CHIP_TAG, newPathChip);

        loadDataSetToUI();

        checkTheSelectedChips(filterPathReader());



    }

    private Chip configureNewPathChip(Chip chipDrawer, int CHIP_TAG) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        Chip newPathChip = (Chip) inflater.inflate(R.layout.chip_path, null, false);


        newPathChip.setText(chipDrawer.getText());
        newPathChip.setId(chipDrawer.getId());
        newPathChip.setTag(CHIP_TAG);

        switch(CHIP_TAG){
        case INSTRUMENT_TAG:

            newPathChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipInstument)));
            break;
        case ARTIST_TAG:

            newPathChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipArtist)));
            break;
        case TYPE_TAG:

            newPathChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipType)));
            break;
        case DURATION_TAG:

            newPathChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.chipDuration)));
            break;
        default:
            break;
        }

        return newPathChip;
    }

    private void addPathchip(String CHIP_TAG, Chip newPathChip) {


        String filterPath = filterPathReader();


        //an to chip of this category exists :::: kane remove, add new at its place
        if (filterPath.contains(CHIP_TAG)) {
            //theoro oti to chip iparxei
            int oldPathChipIndex = filterPath.indexOf(CHIP_TAG);  //might be null?
            if (newPathChip.getId() != mainPathBarLinearLayout.getChildAt(oldPathChipIndex).getId()) {
                //if new chip has diferent ID from old chip , remove old chip and add new chip.
                mainPathBarLinearLayout.removeViewAt(oldPathChipIndex);
                mainPathBarLinearLayout.addView(newPathChip, oldPathChipIndex);

                Toast.makeText(getContext(), "dont replace pathchip ", Toast.LENGTH_SHORT).show();

            }
        } else {


            // if the new chips category does not exist, you have to add the new one but without removing any other chip
            // but in case the 'newChip' is an ArtistTAg chip... you have to add it right after the Instrument chip.
            if (CHIP_TAG.equals(ARTIST_TAG_STRING)) {

                //an index= -1 ,, tha mpei sto -1+1=0 thesi,, logo LOGIC
                int instrumentChipIndex = filterPath.indexOf(String.valueOf(INSTRUMENT_TAG));
                mainPathBarLinearLayout.addView(newPathChip, instrumentChipIndex + 1);
            } else {

                mainPathBarLinearLayout.addView(newPathChip);
            }

        }
    }


    private void loadDataSetToUI() {

        // Fetch the result set once (avoid multiple calls)
        resultSet = getResultSetByFilterPath(filterPathReader());

        if (resultSet != null) {
            // Only call populator if the list is not null
            if (resultSet.getArtistList() != null) {
                artistChipGroupPopulator(resultSet.getArtistList());
            }
            if (resultSet.getInstrumentList() != null) {
                instrumentChipGroupPopulator(resultSet.getInstrumentList());
            }
            if (resultSet.getTypeList() != null) {
                typeChipGroupPopulator(resultSet.getTypeList());
            }
            if (resultSet.getDurationList() != null) {
                durationChipGroupPopulator(resultSet.getDurationList());
            }

            //TODO// LOAD THE VIDEOS
            resetVideoLayout();
            loadVideos(currentPage);

        }
    }

    private void checkTheSelectedChips(String filterPath) {
        // Check and set instrument chip if present
        if (filterPath.contains(INSTRUMENT_TAG_STRING)) {
            int index = filterPath.indexOf(INSTRUMENT_TAG_STRING);
            if (index >= 0 && index < mainPathBarLinearLayout.getChildCount()) {
                int instrumentSelectedChipId = mainPathBarLinearLayout.getChildAt(index).getId();
                if (instrumentChipGroup.getCheckedChipId() != instrumentSelectedChipId) {
                    instrumentChipGroup.check(instrumentSelectedChipId);
                }
            }
        }

        // Check and set artist chip if present
        if (filterPath.contains(ARTIST_TAG_STRING)) {
            int index = filterPath.indexOf(ARTIST_TAG_STRING);
            if (index >= 0 && index < mainPathBarLinearLayout.getChildCount()) {
                int artistSelectedChipId = mainPathBarLinearLayout.getChildAt(index).getId();
                if (artistChipGroup.getCheckedChipId() != artistSelectedChipId) {
                    artistChipGroup.check(artistSelectedChipId);
                    Toast.makeText(getContext(), "String.valueOf(retrievedDataSet.getArtistList())", Toast.LENGTH_SHORT).show();

                }
            }
        }

        // Check and set type chip if present
        if (filterPath.contains(TYPE_TAG_STRING)) {
            int index = filterPath.indexOf(TYPE_TAG_STRING);
            if (index >= 0 && index < mainPathBarLinearLayout.getChildCount()) {
                int typeSelectedChipId = mainPathBarLinearLayout.getChildAt(index).getId();
                if (typeChipGroup.getCheckedChipId() != typeSelectedChipId) {
                    typeChipGroup.check(typeSelectedChipId);
                }
            }
        }

        // Check and set duration chip if present
        if (filterPath.contains(DURATION_TAG_STRING)) {
            int index = filterPath.indexOf(DURATION_TAG_STRING);
            if (index >= 0 && index < mainPathBarLinearLayout.getChildCount()) {
                int durationSelectedChipId = mainPathBarLinearLayout.getChildAt(index).getId();
                if (durationChipGroup.getCheckedChipId() != durationSelectedChipId) {
                    durationChipGroup.check(durationSelectedChipId);
                }
            }
        }
    }


    private void onChipFromDrawerDeselected(String CHIP_TAG) {

        removerPathChip(CHIP_TAG,filterPathReader());

        // if u remove the instrument chip , the artisty will be remove too
        if(CHIP_TAG == String.valueOf(INSTRUMENT_TAG)){

            removerPathChip(ARTIST_TAG_STRING,filterPathReader());
            artistChipGroup.clearCheck();
        }

    loadDataSetToUI();
    checkTheSelectedChips(filterPathReader());

    }

    void removerPathChip(String CHIP_TAG, String pathFilter){
        if(pathFilter.contains(CHIP_TAG)) {

            int oldPathChipIndex = pathFilter.indexOf(CHIP_TAG);
            mainPathBarLinearLayout.removeViewAt(oldPathChipIndex);
        }
    }


    private ResultSetForUI getResultSetByFilterPath(String filterPath) {

        ResultSetForUI retrievedDataSet = new ResultSetForUI();

        filterPath = trimZeros(filterPath);


        //otan exo 2(artist) panta exei to 1(instrument)  mprosta
        if (filterPath.length() == 1) {
            switch (filterPath) {
                case "1": // Instrument
                    //retrievedDataSet.setInstrumentList(cachedData.getInstrumentList());
                    retrievedDataSet.setArtistList(cachedData.getArtistFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId())));
                    //retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId())));
                    //retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId())));


                    //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                    retrievedDataSet.setVideoList(cachedData.getVideoFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId())));
                    break;
                case "3": // type
                    //retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byType").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId())));
                    retrievedDataSet.setArtistList(cachedData.getArtistFilters().get("byType").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId())));
                    //retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byType").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId())));
                    //retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byType").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId())));


                    //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                    retrievedDataSet.setVideoList(cachedData.getVideoFilters().get("byType").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId())));
                    break;
                case "4": // duration


                    //retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byDuration").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId())));
                    retrievedDataSet.setArtistList(cachedData.getArtistFilters().get("byDuration").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId())));
                    //retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byDuration").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId())));
                    //retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byDuration").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId())));


                    //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                    retrievedDataSet.setVideoList(cachedData.getVideoFilters().get("byDuration").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId())));
                    break;
            }
        }

        else if (filterPath.length() == 2) {
            if (filterPath.equals("12")) { // Artist
                //retrievedDataSet.setInstrumentList(cachedData.getInstrumentList());
                retrievedDataSet.setArtistList(cachedData.getArtistFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId())));
                // retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byArtist").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId())));
                //retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byArtist").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId())));


                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                //List<Video> videoByInstrumentList = cachedData.getVideoFilters().get("byInstrument").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId()));
                List<Video> videoByArtistList = cachedData.getVideoFilters().get("byArtist").get(String.valueOf(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId()));
                //videoByInstrumentList.retainAll(videoByArtistList);
                retrievedDataSet.setVideoList(videoByArtistList); //INTERSECTION
            }
        }
                /*

            if (filterPath.equals("31") || filterPath.equals("13")) {
                retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId()));
                retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId()));

                List<Duration> durationByInstrumentList = cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Duration> durationByTypeList = cachedData.getDurationFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                durationByInstrumentList.retainAll(durationByTypeList);
                retrievedDataSet.setDurationList(durationByInstrumentList); //INTERSECTION

                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByTypeList = cachedData.getArtistFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                artistByInstrumentList.retainAll(artistByTypeList);
                retrievedDataSet.setArtistList(artistByInstrumentList); //INTERSECTION


                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByInstrumentList = cachedData.getVideoFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Video> videoByTypeList = cachedData.getVideoFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                videoByInstrumentList.retainAll(videoByTypeList);
                retrievedDataSet.setVideoList(videoByTypeList); //INTERSECTION
            }
            if (filterPath.equals("41") || filterPath.equals("14")) {
                retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId()));
                retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId()));

                List<Type> typeByInstrumentList = cachedData.getTypeFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Type> typeByDurationList = cachedData.getTypeFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                typeByInstrumentList.retainAll(typeByDurationList);
                retrievedDataSet.setTypeList(typeByInstrumentList); //INTERSECTION

                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByDurationList = cachedData.getArtistFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                artistByInstrumentList.retainAll(artistByDurationList);
                retrievedDataSet.setArtistList(artistByInstrumentList); //INTERSECTION

                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByInstrumentList = cachedData.getVideoFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Video> videoByDurationList = cachedData.getVideoFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                videoByInstrumentList.retainAll(videoByDurationList);
                retrievedDataSet.setVideoList(videoByInstrumentList); //INTERSECTION
            }
            if (filterPath.equals("34") || filterPath.equals("43")) {
                retrievedDataSet.setTypeList(cachedData.getTypeFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId()));
                retrievedDataSet.setDurationList(cachedData.getDurationFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId()));

                List<Instrument> instrumentByDurationtList = cachedData.getInstrumentFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Instrument> instrumentByTypeList = cachedData.getInstrumentFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                instrumentByDurationtList.retainAll(instrumentByTypeList);
                retrievedDataSet.setInstrumentList(instrumentByDurationtList); //INTERSECTION

                List<Artist> artistByDurationtList = cachedData.getArtistFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Artist> artistByTypeList = cachedData.getArtistFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                artistByDurationtList.retainAll(artistByTypeList);
                retrievedDataSet.setArtistList(artistByDurationtList); //INTERSECTION

                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByTypeList = cachedData.getVideoFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Video> videoByDurationList = cachedData.getVideoFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                videoByTypeList.retainAll(videoByDurationList);
                retrievedDataSet.setVideoList(videoByTypeList); //INTERSECTION
            }
        }else if (filterPath.length() == 3) {
            if (filterPath.equals("312") || filterPath.equals("123")) {

                List<Type> typeByInstrumentList = cachedData.getTypeFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Type> typeByArtistList = cachedData.getTypeFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                typeByInstrumentList.retainAll(typeByArtistList);
                retrievedDataSet.setTypeList(typeByInstrumentList); //INTERSECTION

                retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId()));

                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByTypeList = cachedData.getArtistFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                artistByInstrumentList.retainAll(artistByTypeList);
                retrievedDataSet.setArtistList(artistByInstrumentList); //INTERSECTION

                List<Duration> durationByInstrumentList = cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Duration> durationByTypeList = cachedData.getDurationFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Duration> durationByArtistList = cachedData.getDurationFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                durationByInstrumentList.retainAll(durationByTypeList);
                durationByInstrumentList.retainAll(durationByArtistList);
                retrievedDataSet.setDurationList(durationByInstrumentList); // DOUBLE INTERSECTION


                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByTypeList = cachedData.getVideoFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Video> videoByArtistList = cachedData.getVideoFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                videoByTypeList.retainAll(videoByArtistList);
                retrievedDataSet.setVideoList(videoByTypeList); //INTERSECTION
            }
            if (filterPath.equals("412") || filterPath.equals("124")) {

                List<Duration> durationByInstrumentList = cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Duration> durationByArtistList = cachedData.getDurationFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                durationByInstrumentList.retainAll(durationByArtistList);
                retrievedDataSet.setDurationList(durationByInstrumentList); //INTERSECTION

                retrievedDataSet.setInstrumentList(cachedData.getInstrumentFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId()));

                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByDurationList = cachedData.getArtistFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                artistByInstrumentList.retainAll(artistByDurationList);
                retrievedDataSet.setArtistList(artistByInstrumentList); //INTERSECTION

                List<Type> typeByInstrumentList = cachedData.getTypeFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Type> typeByDurationList = cachedData.getTypeFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Type> typeByArtistList = cachedData.getTypeFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                typeByInstrumentList.retainAll(typeByDurationList);
                typeByInstrumentList.retainAll(typeByArtistList);
                retrievedDataSet.setTypeList(typeByInstrumentList); // DOUBLE INTERSECTION

                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByDurationList = cachedData.getVideoFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Video> videoByArtistList = cachedData.getVideoFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                videoByDurationList.retainAll(videoByArtistList);
                retrievedDataSet.setVideoList(videoByArtistList); //INTERSECTION

            }
            if (filterPath.equals("431") || filterPath.equals("413") || filterPath.equals("341") || filterPath.equals("314") || filterPath.equals("143") || filterPath.equals("134")) {

                List<Duration> durationByInstrumentList = cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Duration> durationByTypeList = cachedData.getDurationFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                durationByInstrumentList.retainAll(durationByTypeList);
                retrievedDataSet.setDurationList(durationByInstrumentList); //INTERSECTION

                List<Instrument> InstrumentByTypeList = cachedData.getInstrumenFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Instrument> InstrumentByDurationList = cachedData.getInstrumenFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                InstrumentByTypeList.retainAll(InstrumentByDurationList);
                retrievedDataSet.setInstrumentList(InstrumentByTypeList); //INTERSECTION

                List<Type> typeByInstrumentList = cachedData.getTypeFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Type> typeByDurationList = cachedData.getTypeFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                typeByInstrumentList.retainAll(typeByDurationList);
                retrievedDataSet.setTypeList(typeByInstrumentList); //INTERSECTION


                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByDurationList = cachedData.getArtistFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Artist> artistByTypeList = cachedData.getArtistFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                artistByInstrumentList.retainAll(artistByDurationList);
                artistByInstrumentList.retainAll(artistByTypeList);
                retrievedDataSet.setArtistList(artistByInstrumentList); // DOUBLE INTERSECTION



                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByDurationList = cachedData.getVideoFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Video> videoByTypeList = cachedData.getVideoFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Video> videoByInstrumentList = cachedData.getVideoFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                videoByDurationList.retainAll(videoByTypeList);
                videoByDurationList.retainAll(videoByInstrumentList);
                retrievedDataSet.setVideoList(videoByDurationList); //INTERSECTION
            }
        }else if (filterPath.length() == 4) {
            if (filterPath.equals("1234") || filterPath.equals("1243") || filterPath.equals("4312") || filterPath.equals("4123") || filterPath.equals("3412") || filterPath.equals("3124")) {

                //List<Duration> durationByInstrumentList = cachedData.getDurationFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Duration> durationByArtistList = cachedData.getDurationFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                List<Duration> durationByTypeList = cachedData.getDurationFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                durationByArtistList.retainAll(durationByTypeList);
                retrievedDataSet.setDurationList(durationByArtistList); //INTERSECTION

                List<Instrument> InstrumentByTypeList = cachedData.getInstrumenFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Instrument> InstrumentByDurationList = cachedData.getInstrumenFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                InstrumentByTypeList.retainAll(InstrumentByDurationList);
                retrievedDataSet.setInstrumentList(InstrumentByTypeList); //INTERSECTION

                List<Type> typeByArtistList = cachedData.getTypeFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                List<Type> typeByDurationList = cachedData.getTypeFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                typeByArtistList.retainAll(typeByDurationList);
                retrievedDataSet.setTypeList(typeByArtistList); //INTERSECTION


                List<Artist> artistByInstrumentList = cachedData.getArtistFilters().get("byInstrument").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("1")).getId());
                List<Artist> artistByDurationList = cachedData.getArtistFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Artist> artistByTypeList = cachedData.getArtistFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                artistByInstrumentList.retainAll(artistByDurationList);
                artistByInstrumentList.retainAll(artistByTypeList);
                retrievedDataSet.setArtistList(artistByInstrumentList); // DOUBLE INTERSECTION


                //TODO// OTAN KANIS INTERSECT    CONVERT TO HASHSET<>   *10 SPEED
                List<Video> videoByDurationList = cachedData.getVideoFilters().get("byDuration").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("4")).getId());
                List<Video> videoByTypeList = cachedData.getVideoFilters().get("byType").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("3")).getId());
                List<Video> videoByArtistList = cachedData.getVideoFilters().get("byArtist").get(mainPathBarLinearLayout.getChildAt(filterPath.indexOf("2")).getId());
                videoByDurationList.retainAll(videoByTypeList);
                videoByDurationList.retainAll(videoByArtistList);
                retrievedDataSet.setVideoList(videoByDurationList); //INTERSECTION
            }
        }
        */
    else if (filterPath.length() <1){
        retrievedDataSet.setInstrumentList(cachedData.getInstrumentList());
        retrievedDataSet.setArtistList(cachedData.getArtistList());
        retrievedDataSet.setTypeList(cachedData.getTypeList());
        retrievedDataSet.setDurationList(cachedData.getDurationList());

        retrievedDataSet.setVideoList(cachedData.getVideoList());
    }



        Toast.makeText(getContext(), String.valueOf(retrievedDataSet.getArtistList()), Toast.LENGTH_SHORT).show();

        return retrievedDataSet;
    }




    //util
    String trimZeros(String str){
        return  str.replaceAll("^0+|0+$", "");
    }


    public static String filterPathReader(){
        //paragei     ena string apo ta ID tou kath chip
        //koitaei to pathLinearLayout , kai fortonei ta id tou kathe paidiou se ena String!
        //to string thelo na exei 4 stoixia max! , sthn sinexeia siblironei me 0s  (zeros)
        StringBuffer pathFilter = new StringBuffer("");

        int i=0;
        int j=0;

        while(i < mainPathBarLinearLayout.getChildCount()) {
            pathFilter.append(mainPathBarLinearLayout.getChildAt(i).getTag());
            i++;
            j++;
        }

        while(j < 4) {

            pathFilter.append(0);
            j++;
        }

        return pathFilter.toString();
    }








    private void setupRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(requireContext());
        videoRecyclerView.setLayoutManager(linearLayoutManager);

        videoAdapter = new VideoAdapter(requireContext(), new ArrayList<>(), getViewLifecycleOwner());
        videoRecyclerView.setAdapter(videoAdapter);

        // Add scroll listener for pagination
        videoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                // More reliable pagination trigger
                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition + VISIBLE_THRESHOLD) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= ITEMS_PER_PAGE) {
                        loadNextPage();
                    }
                }
            }
        });
    }

    private List<Video> getVideosForPage(int page) {
        List<Video> pageVideos = new ArrayList<>();
        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, resultSet.getVideoList().size());

        for (int i = start; i < end; i++) {
            pageVideos.add(resultSet.getVideoList().get(i));
        }

        return pageVideos;
    }

    private void refreshVideoLayout() {
        currentPage = 1;
        isLastPage = false;
        videoAdapter = new VideoAdapter(requireContext(), new ArrayList<>(), getViewLifecycleOwner());
        videoRecyclerView.setAdapter(videoAdapter);
        loadVideos(currentPage);

        swipeRefreshLayout.setRefreshing(false);

        Toast.makeText(getContext(), "haha refresh works", Toast.LENGTH_SHORT).show();

    }

    private void resetVideoLayout() {
        currentPage = 1;
        isLastPage = false;
        videoAdapter = new VideoAdapter(requireContext(), new ArrayList<>(), getViewLifecycleOwner());
        videoRecyclerView.setAdapter(videoAdapter);
    }



}