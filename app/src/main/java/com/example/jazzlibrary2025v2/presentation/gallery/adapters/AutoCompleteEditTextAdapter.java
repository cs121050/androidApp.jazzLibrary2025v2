package com.example.jazzlibrary2025v2.presentation.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jazzlibrary2025v2.R;
import com.example.jazzlibrary2025v2.domain.model.Artist;
import com.example.jazzlibrary2025v2.domain.model.Video;

import java.util.ArrayList;
import java.util.List;
public class AutoCompleteEditTextAdapter extends ArrayAdapter<Object> {
    private final List<Object> items;
    private final LayoutInflater inflater;
    private List<Object> filteredItems;
    private ItemFilter filter = new ItemFilter();

    public AutoCompleteEditTextAdapter(Context context, List<Object> items) {
        super(context, R.layout.autocomplete_item, items);
        this.items = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.autocomplete_item, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.autocomplete_suggestion_resource_icon);
            holder.title = convertView.findViewById(R.id.autocomplete_suggestion_resource_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object item = getItem(position);
        if (item instanceof Artist) {
//            Toast.makeText(getContext(), "this item is an artist", Toast.LENGTH_SHORT).show();

            Artist artist = (Artist) item;
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(getInstrumentIconResId(artist.getInstrumentId()));
            holder.title.setText(artist.getArtistName() + " " + artist.getArtistSurname());

        } else if (item instanceof Video) {
            Video video = (Video) item;
            holder.icon.setVisibility(View.GONE);
            holder.title.setText(video.getVideoName());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView title;
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Object> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(items);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Object item : items) {
                    if (item instanceof Artist) {
                        Artist artist = (Artist) item;
                        String artistName = artist.getArtistName() + " " + artist.getArtistSurname();
                        if (artistName.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    } else if (item instanceof Video) {
                        Video video = (Video) item;
                        if (video.getVideoName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems.clear();
            if (results.values != null) {
                filteredItems.addAll((List<Object>) results.values);
            }
            notifyDataSetChanged();
        }
    }



    private int getInstrumentIconResId(int instrumentId) {
        switch (instrumentId) {
            case 1:  // Bass
                return R.drawable.instrument_icon_bass;
            case 2:  // Guitar
                return R.drawable.instrument_icon_guitar;
            case 3:  // Piano
                return R.drawable.instrument_icon_piano;
            case 4:  // Drums
                return R.drawable.instrument_icon_drums;
            case 5:  // voice
                return R.drawable.instrument_icon_voice;
            case 6:  // sax
                return R.drawable.instrument_icon_sax;
            case 7:  // trumpet
                return R.drawable.instrument_icon_trumpet;
            case 8:  // violin
                return R.drawable.instrument_icon_violin;
            case 9:  // vibes
                return R.drawable.instrument_icon_vibes;
            case 10:  // clarinete
                return R.drawable.instrument_icon_clarinete;
            case 11:  // trombone
                return R.drawable.instrument_icon_trombone;
            case 12:  // journalism
                return R.drawable.instrument_icon_journalism;
            case 13:  // other
                return R.drawable.instrument_icon_other;
            default:
                return R.drawable.instrument_icon_other; // Default icon
        }
    }




}