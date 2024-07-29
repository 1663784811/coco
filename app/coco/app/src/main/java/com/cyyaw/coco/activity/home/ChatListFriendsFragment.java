package com.cyyaw.coco.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cyyaw.coco.R;
import com.cyyaw.coco.activity.PersonCenterActivity;
import com.cyyaw.coco.dao.FriendsDao;
import com.cyyaw.coco.dao.table.FriendsEntity;
import com.cyyaw.cui.fragment.CuiEmptyFragment;
import com.cyyaw.cui.fragment.CuiFriendsListItemFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 好友列表
 */
public class ChatListFriendsFragment extends Fragment implements FriendsDao.UpdateDataCallBack {

    private static final String TAG = "ChatListFriendsView";

    private Fragment cuiEmpty = null;

    private List<FriendsEntity> friendsList = new ArrayList<>();
    private Map<String, Fragment> fragmentMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_friends, container, false);
        view.findViewById(R.id.friendList);
        // 同步好友
        updateData();
        FriendsDao.setUpdateDataCallBack(this);


        return view;
    }

    @Override
    public void updateData() {
        friendsList = FriendsDao.getFriends();
        if (null != friendsList && friendsList.size() > 0) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();

            for (int i = 0; i < friendsList.size(); i++) {
                FriendsEntity friendsEntity = friendsList.get(i);
                String tid = friendsEntity.getTid();
                Fragment fragment = fragmentMap.get(tid);
                if (null == fragment) {
                    String nickName = friendsEntity.getNickName();
                    String face = friendsEntity.getFace();
                    CuiFriendsListItemFragment fr = new CuiFriendsListItemFragment(face, nickName, (View v) -> {
                        PersonCenterActivity.openActivity(getContext(), tid, nickName, face);
                    });
                    fragmentMap.put(tid, fr);
                    ft.add(R.id.friendList, fr);
                }
            }
            if (cuiEmpty != null) {
                ft.remove(cuiEmpty);
                cuiEmpty = null;
            }
            ft.commit();
        } else {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            for (String key : fragmentMap.keySet()) {
                Fragment fragment = fragmentMap.get(key);
                ft.remove(fragment);
            }
            if (cuiEmpty == null) {
                cuiEmpty = new CuiEmptyFragment();
                ft.add(R.id.friendList, cuiEmpty);
            }
            ft.commit();
        }
    }
}
