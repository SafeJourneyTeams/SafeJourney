package rsta.safejourney;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by user on 1/9/2018.
 */

public class tabsPager extends FragmentStatePagerAdapter {

    String[] titles = new String[] {"Home", "Complaint", "Safety Tip", "First Aid"};

    public tabsPager(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0:
                HomeBlankFragment blankFragment = new HomeBlankFragment();
                return blankFragment;

            case 1:
                ComplaintBlankFragment blank1Fragment = new ComplaintBlankFragment();
                return blank1Fragment;

            case 2:
                SafetyTipsBlankFragment blank2Fragment = new SafetyTipsBlankFragment();
                return blank2Fragment;

            case 3:
                FirstAidBlankFragment blank3Fragment = new FirstAidBlankFragment();
                return blank3Fragment;


        }

        return null;
    }

    @Override
    public int getCount() {

        return 4;
    }

    @Override
    public CharSequence getPageTitle (int position){
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Complaint";
            case 2:
                return "Safety Tips";
            case 3:
                return "First Aids";
            default:
                return null;
        }
    }
}
