package com.thatdubaigirl.com.Utils;

import androidx.fragment.app.Fragment;

public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImplimentation(this).onBackPressed();
    }
}