package com.example.whatsapp_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter
{

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;

            case 1:
                GroupsFragment groupsFragment = new GroupsFragment();
                return groupsFragment;

            case 2:
                ContractsFragment contractsFragment = new ContractsFragment();
                return contractsFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    //to set the title for all the fragments we all use the below method

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                ChatFragment chatFragment = new ChatFragment();
                return "Chats";

            case 1:
                GroupsFragment groupsFragment = new GroupsFragment();
                return "Groups";

            case 2:
                ContractsFragment contractsFragment = new ContractsFragment();
                return "Contracts";

            default:
                return null;
        }
    }
}
