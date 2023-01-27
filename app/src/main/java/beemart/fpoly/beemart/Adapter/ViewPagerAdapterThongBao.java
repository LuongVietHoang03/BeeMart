package beemart.fpoly.beemart.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import beemart.fpoly.beemart.Fragment.FragmentChoXacNhan;
import beemart.fpoly.beemart.Fragment.FragmentDaXacNhan;
import beemart.fpoly.beemart.Fragment.FragmentDangGiao;
import beemart.fpoly.beemart.Fragment.FragmentGiaoXong;
import beemart.fpoly.beemart.ThongBao.DonHangFragment;
import beemart.fpoly.beemart.ThongBao.NapTienFragment;

public class ViewPagerAdapterThongBao extends FragmentStatePagerAdapter {

    public ViewPagerAdapterThongBao(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DonHangFragment();

            case 1:
                return new NapTienFragment();


            default:
                return new DonHangFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đơn Hàng";
                break;

            case 1:
                title = "Nạp Tiền";
                break;

        }
        return title;
    }
}
