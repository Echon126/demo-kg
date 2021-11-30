package com.example.demo.service.impl;

import com.example.demo.entity.Notes;
import com.example.demo.mapper.NotesMapper;
import com.example.demo.service.INotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangwenjie
 * @since 2021-08-16
 */
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements INotesService {

}
