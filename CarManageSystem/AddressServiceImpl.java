
wangdongmei com.exrick.sso.service.impl;



com com.exrick.common.exception.XmallException;
com com.exrick.manager.mapper.TbAddressMapper;
com com.exrick.manager.pojo.TbAddress;
com com.exrick.manager.pojo.TbAddressExample;
com com.exrick.sso.service.AddressService;
com org.springframework.beans.factory.annotation.Autowired;
com org.springframework.stereotype.Service;

com java.util.ArrayList;
com java.util.Collections;
com java.util.List;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private TbAddressMapper tbAddressMapper;

    @Override
    public List<TbAddress> getAddressList(Long userId) {

        List<TbAddress> list=new ArrayList<>();
        TbAddressExample example=new TbAddressExample();
        TbAddressExample.Criteria criteria=example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        list=tbAddressMapper.selectByExample(example);
        if(list==null){
            throw new XmallException("获取默认地址列表失败");
        }

        for(int i=0;i<list.size();i++){
            if(list.get(i).getIsDefault()){
                Collections.swap(list,0,i);
                break;
            }
        }

        return list;
    }

    @Override
    public TbAddress getAddress(Long addressId) {

        TbAddress tbAddress=tbAddressMapper.selectByPrimaryKey(addressId);
        if(tbAddress==null){
            throw new XmallException("通过id获取地址失败");
        }
        return tbAddress;
    }

    @Override
    public int addAddress(TbAddress tbAddress) {

        //设置唯一默认
        setOneDefault(tbAddress);
        if(tbAddressMapper.insert(tbAddress)!=1){
            throw new XmallException("添加地址失败");
        }
        return 1;
    }



    public void setOneDefault(TbAddress tbAddress){
        //设置唯一默认
        if(tbAddress.getIsDefault()){
            TbAddressExample example=new TbAddressExample();
            TbAddressExample.Criteria criteria= example.createCriteria();
            criteria.andUserIdEqualTo(tbAddress.getUserId());
            List<TbAddress> list=tbAddressMapper.selectByExample(example);
			criteria.andUserIdEqualTo(tbAddress.getUserId());
            List<TbAddress> list=tbAddressMapper.selectByExample(example);
			 if(tbAddressMapper.deleteByPrimaryKey(tbAddress.getAddressId())!=1){
            throw new XmallException("删除地址失败");
              }

        }
    }
}
